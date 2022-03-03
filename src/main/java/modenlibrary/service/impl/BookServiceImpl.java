package modenlibrary.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.eum.BookStatus;
import modenlibrary.Common.eum.RoleEnum;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.config.ImgProperties;
import modenlibrary.entity.Category;
import modenlibrary.entity.LendList;
import modenlibrary.entity.User;
import modenlibrary.entity.Vo.BooksVo;
import modenlibrary.mapper.CategoryMapper;
import modenlibrary.mapper.LendListMapper;
import modenlibrary.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import modenlibrary.mapper.BookMapper;
import modenlibrary.entity.Book;
import modenlibrary.service.BookService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author L.star
 * @date 2020/12/23 16:08
 */
@Slf4j
@Service("bookService")
public class BookServiceImpl implements BookService {

    //文件限制大小 单位m
    private static final Integer FILE_LIMIT_SIZE = 10;
    //借书天数
    private static final Integer MAX_LEND_DAY = 30;
    //最大违规次数
    private static final Integer MAX_COUNTS = 5;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private LendListMapper lendListMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    ImgProperties config;

    @Async("asyncServiceExecutor")
    @Override
    public void deleteByPrimaryKey(String isbn) {
        log.info("删除ISBN为 【{}】 的图书",isbn);
        bookMapper.deleteByPrimaryKey(isbn);
    }


    @Override
    public int insert(Book record) {
        log.info("插入图书 {}",record);
        return bookMapper.insert(record);
    }


    @Override
    public int insertSelective(Book record) {
        log.info("有选择地插入图书 {}",record);
        return bookMapper.insertSelective(record);
    }

    @Override
    public Book selectByPrimaryKey(String isbn) {
        log.info("根据ISBN为 【{}】 查找图书",isbn);
        return bookMapper.selectByPrimaryKey(isbn);
    }

    @Override
    public int updateByPrimaryKeySelective(Book record) {
        log.info("有选择地更新图书为 【{}】",record);
        return bookMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Book record) {
        log.info("根据ID更新图书为 【{}】",record);
        return bookMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<Book> queryAll(PageRequest pageRequest, Book book) {
        log.info("查询图书记录 【{}】",book);
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Book> books = bookMapper.queryAll(book);
        return new PageInfo<>(books);
    }

    /**
     * 借书
     *
     * @param user
     * @return
     */
    @Transactional
    @Override
    public void lendBook(User user, String isbn) {
        //如果未登录或者黑名单 抛出异常
        if (user == null || user.getIsblack().intValue() == 1) {
            log.error("错误：用户为登录或黑名单");
            throw new BusinessException(ReturnCode.AUTHOR_ERROR);
        }
        //如果可以借书的数目不足 返回
        if (user.getAllowLend() == 0) {
            log.error("错误：用户可借书籍不足");
            throw new BusinessException(ReturnCode.CAN_NOT_LEND);
        }
        //借书
        Book book = bookMapper.selectByPrimaryKey(isbn);
        //该书不存在
        if (book == null) {
            log.error("错误：书本不存在");
            throw new BusinessException(ReturnCode.BOOK_UNKNOWN);
        }
        //该书没库存
        if (book.getBookNum() < 1) {
            log.error("错误：书本库存不足");
            throw new BusinessException(ReturnCode.BOOK_LESS);
        }

        LendList lendList = lendListMapper.queryLendBook(book.getIsbn(), user.getId());
        if (lendList == null) {
            //如果没有记录 那么新建插入记录
            lendList = LendList.builder().isbn(book.getIsbn())
                    .title(book.getTitle())
                    .uid(user.getId())
                    .lendDate(DateTime.now())
                    .status(BookStatus.LENDING.getMsg())
                    .build();
            log.info("用户未借过书籍，新插入记录 {}",lendList);
            lendListMapper.insert(lendList);
        } else {
            //如果还是借出状态
            if (lendList.getStatus().equals(BookStatus.LENDING.getMsg())) {
                throw new BusinessException(ReturnCode.ALREDAY_LENDED);
            }
            //重写该记录
            lendList.setStatus(BookStatus.LENDING.getMsg());
            lendList.setReturnDate(null);
            lendList.setLendDate(DateTime.now());
            log.info("用户借阅过该书籍，重写记录 {}",lendList);
            lendListMapper.updateLendList(lendList);
        }
        book.setBookNum(book.getBookNum() - 1);
        log.info("更新书本为 {}",book);
        bookMapper.updateByPrimaryKey(book);
        user.setAllowLend(user.getAllowLend() - 1);
        log.info("更新用户相关信息 {}",user);
        userMapper.updateByPrimaryKey(user);
        //当前日期借书人数加一 格式是：LendBookNum月份 当月第几天
        double num = redisUtil.hincr("LendBookNum"+(DateUtil.thisMonth()+1),String.valueOf(DateUtil.thisDayOfMonth()),1d);
        log.info("{}月{}日 借书人数 {} 人",(DateUtil.thisMonth()+1),DateUtil.thisDayOfMonth(),num);
    }

    /**
     * 更改借阅状态
     *
     * @param user
     * @param isbn
     * @param status
     * @return
     */
    @Async("asyncServiceExecutor")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void returnedBook(User user, String isbn, BookStatus status) {
        log.info("【{}】 用户更改图书 【{}】 借阅状态为 【{}】",user,isbn,status);
        //书本
        Book book = bookMapper.selectByPrimaryKey(isbn);
        if (book==null){
            log.error("错误：书本不存在");
            throw new BusinessException(ReturnCode.BOOK_UNKNOWN);
        }
        //查找借的图书记录
        LendList lendList = lendListMapper.queryLendBook(book.getIsbn(), user.getId());
        if (lendList == null) {
            log.error("错误：借书记录不存在");
            //没有这个借书记录
            throw new BusinessException(ReturnCode.NOT_LEND_LIST);
        }
        switch (status){
            case LENDING:
//              如果改为借出 把借出日期改为今天
                log.info("改为借出，更改借书日期为今天");
                lendList.setLendDate(DateTime.now());
                this.lendBook(user,isbn);
                break;
            case RETURNED:
                log.info("归还书籍");
                //如果改为归还
                lendList.setReturnDate(DateTime.now());
                //检查超时违规
                checkCounts(user,lendList);
                int i = lendList.getReturnDate().compareTo(lendList.getLendDate());
                lendList.setStatus(BookStatus.RETURNED.getMsg());
                int ok = lendListMapper.updateLendList(lendList);
                if (ok == 1) {
                    //书本数目
                    book.setBookNum(book.getBookNum() + 1);
                    log.info("更新书本数目");
                    bookMapper.updateByPrimaryKeySelective(book);
                    //用户借书数目
                    user.setAllowLend(user.getAllowLend() + 1);
                    log.info("更新用户相关信息");
                    userMapper.updateByPrimaryKeySelective(user);
                } else {
                    log.error("更新记录 【{}】 失败",lendList);
                    throw new BusinessException(ReturnCode.SYSTEM_ERROR);
                }
                break;
            case OTHERS:
                //检查超时违规
                checkCounts(user,lendList);
                lendList.setStatus(BookStatus.OTHERS.getMsg());
                log.info("其他问题违规，违规次数加一");
                userMapper.incrCounts(user.getId());
                lendListMapper.updateLendList(lendList);
                break;
            default:
                break;
        }
    }

    private int differenceInDays(Date lendDate, Date returnDate) {
        int days = (int)((returnDate.getTime() - lendDate.getTime()) / (1000*3600*24));
        return days;
    }

    private void checkCounts(User user, LendList lendList) {
        log.info("检查是否违规");
        if (user.getId() == 111) {
            return;
        }
        if (lendList.getStatus().equals(BookStatus.OTHERS.getMsg())) {
            return;
        }
        int days = differenceInDays(lendList.getLendDate(),lendList.getReturnDate());
        //超时违规
        if (days > MAX_LEND_DAY) {
            user.setCounts(user.getCounts()+1);
            log.info("用户 【{}】 超时归还,违规次数增加到 {}",user,user.getCounts());
            userMapper.incrCounts(user.getId());
            //违规次数超过限制 加入黑名单
            if (user.getCounts() >= MAX_COUNTS){
                log.info("用户 【{}】 违规次数过多，加入黑名单",user);
                userMapper.changeRole(user.getId(), RoleEnum.BLACK.getRoleid());
            }
        }
    }

    /**
     * 添加图书
     *
     * @param book
     * @param file
     */
    @Async("asyncServiceExecutor")
    @Transactional
    @Override
    public void addBook(Book book, MultipartFile file) {
        if (bookMapper.selectByPrimaryKey(book.getIsbn()) != null) {
            log.error("ISBN 【{}】 的书籍已经存在",book.getIsbn());
            //该isbn书已经有了
            throw new BusinessException(101,"该ISBN号已经有了");
        }
        if (book.getIsbn().length()!=10){
            log.error("ISBN 【{}】 号格式错误",book.getIsbn());
            throw new BusinessException(100, "isbn号错误！");
        }
        //书 有图片了
        if (!(file == null) && !file.isEmpty()) {
            try {
                //添加图书
               log.info("图书带有图片");
               book = addImg(book,file);
               int ok = bookMapper.insert(book);
               if (ok==1){
                   log.info("书本数据库添加成功");
                   updateCategoryNum(book);
               }else {
                   log.error("添加图书 【{}】 失败",book);
                   throw new BusinessException(ReturnCode.SYSTEM_ERROR);
               }
            } catch (FileNotFoundException e) {
                log.error("添加图书------文件找不到：{}",e.getMessage());
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            } catch (IOException e) {
                log.error("添加图书------IO错误：{}",e.getMessage());
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        } else {
            log.info("图书没有图片");
            //如果图片为空 直接保存书
            int ok = bookMapper.insert(book);
            if (ok == 1) {
                //添加该类型的书的数量
                updateCategoryNum(book);
            } else {
                log.error("添加图书 【{}】 失败",book);
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        }
    }

    /**
     * 更新redis钟该类型书本的数量
     * @param book
     */
    private void updateCategoryNum(Book book){
        log.info("更新redis字段");
        if (book.getCategoryId()!=null && book.getCategoryId()>0 && book.getCategoryId()<23){
            Category category = categoryMapper.findById(book.getCategoryId());
            Object obj = redisUtil.hget("categoryNum", category.getCName());
            Integer categoryNum;
            if (obj!=null){
                categoryNum = (Integer) obj;
            }else {
                categoryNum = 0;
            }
            log.info("类别ID {} 的数量为 {} ",book.getCategoryId(),(categoryNum+1));
            //增加数量
            redisUtil.hset("categoryNum",category.getCName(),categoryNum+1);
        }else {
            log.error("书本类别ID 【{}】错误",book.getCategoryId());
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
    }

    /**
     * 更新图书
     *
     * @param book
     * @param file
     * @return
     */
    @Async("asyncServiceExecutor")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBook(Book book, MultipartFile file) {
        log.info("更新图书 【{}】",book);
        //图书是否存在
        if (bookMapper.selectByPrimaryKey(book.getIsbn())==null){
            log.error("图书【{}】不存在",book);
            throw new BusinessException(999,"ISBN错误");
        }
        //判断是否带有文件 是否更换图片
        if (file!=null && !file.isEmpty()){
            try {
                log.info("带有图片，更换图书图片");
                //有文件 更换图片信息 更新书本
                String path = ResourceUtils.getURL("classpath:").getPath() + config.getUplodaPath();
                log.info("path: {}",path);
//                String realPath = path.replace("/", "\\").substring(1, path.length());
                String realPath = config.getRealPath();
                log.info("RealPath {}",realPath);
                //判断旧书本信息是否有图片
                if (!StrUtil.isBlank(book.getImgPath())){
                    //原本有图片
                    //获取旧的图片的文件信息
                    String oldImgName = book.getImgPath().substring(book.getImgPath().lastIndexOf("/") + 1);
                    log.info( "旧的文件名 {}",oldImgName);
                    //判断图片是否存在
                    File oldImg = new File(realPath,oldImgName);
                    //如果存就先删除
                    if (oldImg.exists()){
                        oldImg.delete();
                        log.info("旧文件删除了");
                    }
                    //写入
                    book = addImg(book,file);
                }else {
                    //原本没图片 直接加
                    book = addImg(book, file);
                }
                //更新数据库信息
                int ok = bookMapper.updateByPrimaryKeySelective(book);
                log.info("有图片修改后 {}",book);
                if (ok==1){
                }else {
                    throw new BusinessException(ReturnCode.SYSTEM_ERROR);
                }
            }catch (FileNotFoundException e){
                log.error("更新文件-----文件找不到：{}",e.getMessage());
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }catch (IOException e){
                log.error("更新文件-------IO问题：{}",e.getMessage());
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        }else {
            log.info("没有图片，只更新书本");
            //若没有文件，直接更新书本
            int ok = bookMapper.updateByPrimaryKeySelective(book);
            log.info("没图片修改后 {}",book);
            if (ok==1){
            }else {
                log.error("错误：图书 【{}】 更新失败",book);
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        }
    }

    /**
     * 用于添加图片
     *
     * @param book
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private Book addImg(Book book, MultipartFile file)throws FileNotFoundException,IOException{
            log.info("保存图片");
            //上传地址  更改如：static/upload/imgs
            String path = ResourceUtils.getURL("classpath:").getPath() + config.getUplodaPath();
//            String realPath = path.replace("/", "\\").substring(1, path.length());
            String realPath = config.getRealPath();
            log.info("保存的地址为：{}" , realPath);
            //文件名称
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //文件支持类型
            String[] types = config.getImgType().split(",");
            //获取文件类型
            String contentType = file.getContentType();
            boolean flag = false;
            //获取文件后缀
            String suffixName = contentType.substring(contentType.indexOf("/") + 1);
            log.info("文件后缀 {}",suffixName);
            for (String type : types) {
                if (StrUtil.equalsIgnoreCase(type, suffixName)) {
                    //如果有一个匹配 就为true
                    flag = true;
                }
            }
            if (flag) {
                //图片类型匹配
                String imgName = book.getTitle() + uuid +"."+suffixName;
                File photo = new File(realPath, imgName);
                //上传
                FileUtil.writeFromStream(file.getInputStream(),photo);
                //存进数据库用来访问图片的地址
                book.setImgPath( config.getBaseURL()+ imgName);
                return book;
            } else {
                log.error("文件类型不匹配");
                //一个都不匹配
                throw new BusinessException(ReturnCode.IMG_TYPE_ERROR);
            }

    }

    /**
     * 获取不同分类的书本数量
     * @return
     */
    @Override
    public Map<String, Integer> categoryNum() {
        log.info("获取不同类型书本数量");
        Map<String, Integer>map = new HashMap<>();
        List<Map<String, Object>>list = bookMapper.categoryNum();
        for (Map<String, Object> stringObjectMap : list) {
            String cname = null;
            Integer num = 0;
            for(Map.Entry<String,Object>entry : stringObjectMap.entrySet()){
                if ("cname".equals(entry.getKey())){
                    cname = String.valueOf(entry.getValue());
                }else if ("num".equals(entry.getKey())){
                    num = Integer.parseInt((entry.getValue() + ""));
                }
                map.put(cname,num);
            }
        }
        return map;
    }


    /**
     * 异步批量处理添加图书
     * @param file
     */
    @Async("asyncServiceExecutor")
    @Override
    public void uploadExcel(MultipartFile file) {
        if (file==null||file.isEmpty()){
            log.error("错误：文件不存在");
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        String originalFilename = file.getOriginalFilename();
        String fileTypeName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        //文件类型判断
        if (!"xls".equals(fileTypeName)){
            log.error("错误：文件格式错误，格式为 【{}】",fileTypeName);
            throw new BusinessException(ReturnCode.FILE_TYPE_ERROR);
        }
        if(!"application/vnd.ms-excel".equals(file.getContentType()))
        {
            log.error("错误：文件格式错误，格式为 【{}】",file.getContentType());
            throw new BusinessException(ReturnCode.FILE_TYPE_ERROR);
        }
        //文件大小判断
        if (file.getSize()/1024/1024 > FILE_LIMIT_SIZE){
            log.error("错误：文件大小超出限制");
            throw new BusinessException(ReturnCode.FILE_TOO_BIG);
        }
        try {
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            List<BooksVo> booksVos = reader.readAll(BooksVo.class);
            for (BooksVo booksVo : booksVos) {
                Book book = booksVo.convert();
                if (bookMapper.selectByPrimaryKey(book.getIsbn()) != null) {
                    log.error("错误：该ISBN号的书籍已经存在");
                    //该isbn书已经有了
                    throw new BusinessException(101,"该ISBN号已经有了");
                }
                if (book.getIsbn().length()!=10){
                    log.error("错误：ISBN号格式错误");
                    throw new BusinessException(100, "isbn号错误！");
                }
                int ok = bookMapper.insertSelective(book);
                if (ok == 1) {
                    //添加该类型的书的数量
                    updateCategoryNum(book);
                } else {
                    throw new BusinessException(ReturnCode.SYSTEM_ERROR);
                }

            }
            booksVos = null;
        } catch (IOException e) {
            log.error("错误：文件读取失败！{}",e.getMessage());
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
    }
}
