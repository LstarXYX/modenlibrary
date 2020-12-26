package modenlibrary.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.eum.BookStatus;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.RedisUtil;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.config.ImgProperties;
import modenlibrary.entity.LendList;
import modenlibrary.entity.User;
import modenlibrary.mapper.LendListMapper;
import modenlibrary.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import modenlibrary.mapper.BookMapper;
import modenlibrary.entity.Book;
import modenlibrary.service.BookService;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * @author L.star
 * @date 2020/12/23 16:08
 */
@Service
public class BookServiceImpl implements BookService {

    private static Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Resource
    private BookMapper bookMapper;

    @Resource
    private LendListMapper lendListMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    ImgProperties config;

    @Override
    public int deleteByPrimaryKey(String isbn) {
        return bookMapper.deleteByPrimaryKey(isbn);
    }

    @Override
    public int insert(Book record) {
        return bookMapper.insert(record);
    }

    @Override
    public int insertSelective(Book record) {
        return bookMapper.insertSelective(record);
    }

    @Override
    public Book selectByPrimaryKey(String isbn) {
        return bookMapper.selectByPrimaryKey(isbn);
    }

    @Override
    public int updateByPrimaryKeySelective(Book record) {
        return bookMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Book record) {
        return bookMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<Book> queryAll(PageRequest pageRequest, Book book) {
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
    @Override
    public void lendBook(User user, String isbn) {
        //如果未登录或者黑名单 抛出异常
        if (user == null || user.getIsblack().intValue() == 1) {
            throw new BusinessException(ReturnCode.AUTHOR_ERROR);
        }
        //如果可以借书的数目不足 返回
        if (user.getAllowLend() == 0) {
            throw new BusinessException(ReturnCode.CAN_NOT_LEND);
        }
        //借书
        Book book = bookMapper.selectByPrimaryKey(isbn);
        //该书不存在
        if (book == null) {
            throw new BusinessException(ReturnCode.BOOK_UNKNOWN);
        }
        //该书没库存
        if (book.getBookNum() < 1) {
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
            lendListMapper.insert(lendList);
        } else {
            //重写该记录
            lendList.setStatus(BookStatus.LENDING.getMsg());
            lendList.setReturnDate(null);
            lendList.setLendDate(DateTime.now());
            lendListMapper.updateLendList(lendList);
        }
        book.setBookNum(book.getBookNum() - 1);
        bookMapper.updateByPrimaryKey(book);
        user.setAllowLend(user.getAllowLend() - 1);
        userMapper.updateByPrimaryKey(user);
        //当前日期借书人数加一 格式是：LendBookNum月份 当月第几天
        double num = redisUtil.hincr("LendBookNum"+DateUtil.thisMonth(),String.valueOf(DateUtil.thisDayOfMonth()),1);
        logger.info("当天借书人数："+num+"人");
    }

    /**
     * 还书
     *
     * @param user
     * @param isbn
     * @param status
     * @return
     */
    @Override
    public Boolean returnedBook(User user, String isbn, BookStatus status) {
        //书本
        Book book = bookMapper.selectByPrimaryKey(isbn);
        //查找借的图书记录
        LendList lendList = lendListMapper.queryLendBook(book.getIsbn(), user.getId());
        if (lendList == null) {
            //没有这个借书记录
            throw new BusinessException(ReturnCode.NOT_LEND_LIST);
        }
        //
        lendList.setReturnDate(DateTime.now());
        lendList.setStatus(BookStatus.RETURNED.getMsg());
        int ok = lendListMapper.updateLendList(lendList);
        if (ok == 1) {
            //书本数目
            book.setBookNum(book.getBookNum() + 1);
            bookMapper.updateByPrimaryKeySelective(book);
            //用户借书数目
            user.setAllowLend(user.getAllowLend() + 1);
            userMapper.updateByPrimaryKeySelective(user);
            return true;
        } else {
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
    }

    /**
     * 添加图书
     *
     * @param book
     * @param file
     */
    @Override
    public Book addBook(Book book, MultipartFile file) {
        if (bookMapper.selectByPrimaryKey(book.getIsbn()) != null) {
            //该isbn书已经有了
            throw new BusinessException(ReturnCode.SYSTEM_ERROR);
        }
        if (book.getIsbn().length()!=10){
            throw new BusinessException(100, "isbn号错误！");
        }
        //书 有图片了
        if (!(file == null) && !file.isEmpty()) {
            try {
                //添加图书
               book = addImg(book,file);
               int ok = bookMapper.insert(book);
               if (ok==1){
                   return book;
               }else {
                   throw new BusinessException(ReturnCode.SYSTEM_ERROR);
               }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                logger.error("添加图书------文件找不到");
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("添加图书------IO错误");
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        } else {
            //如果图片为空 直接保存书
            int ok = bookMapper.insert(book);
            if (ok == 1) {
                return book;
            } else {
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        }
    }

    /**
     * 更新图书
     *
     * @param book
     * @param file
     * @return
     */
    @Override
    public Book updateBook(Book book, MultipartFile file) {
        //图书是否存在
        book = bookMapper.selectByPrimaryKey(book.getIsbn());
        if (book==null){
            throw new BusinessException(999,"ISBN错误");
        }
        //判断是否带有文件 是否更换图片
        if (file!=null && !file.isEmpty()){
            try {
                //有文件 更换图片信息 更新书本
                String path = ResourceUtils.getURL("classpath:").getPath() + config.getUplodaPath();
                String realPath = path.replace("/", "\\").substring(1, path.length());
                //判断旧书本信息是否有图片
                if (!StrUtil.isBlank(book.getImgPath())){
                    //原本有图片
                    //获取旧的图片的文件信息
                    String oldImgName = book.getImgPath().substring(book.getImgPath().lastIndexOf("/") + 1);
                    logger.info( "旧的文件名-------"+oldImgName);
                    //判断图片是否存在
                    File oldImg = new File(realPath,oldImgName);
                    //如果存就先删除
                    if (oldImg.exists()){
                        oldImg.delete();
                        logger.info("旧文件删除了");
                    }
                    //写入
                    book = addImg(book,file);
                }else {
                    //原本没图片 直接加
                    book = addImg(book, file);
                }
                //更新数据库信息
                int ok = bookMapper.updateByPrimaryKeySelective(book);
                if (ok==1){
                    return book;
                }else {
                    throw new BusinessException(ReturnCode.SYSTEM_ERROR);
                }
            }catch (FileNotFoundException e){
                e.printStackTrace();
                logger.error("更新文件-----文件找不到");
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }catch (IOException e){
                e.printStackTrace();
                logger.error("更新文件-------IO问题");
                throw new BusinessException(ReturnCode.SYSTEM_ERROR);
            }
        }else {
            //若没有文件，直接更新书本
            int ok = bookMapper.updateByPrimaryKeySelective(book);
            if (ok==1){
                return book;
            }else {
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
            //上传地址  更改如：static/upload/imgs
            String path = ResourceUtils.getURL("classpath:").getPath() + config.getUplodaPath();
            String realPath = path.replace("/", "\\").substring(1, path.length());
            logger.info("保存的地址为：" + realPath);
            //文件名称
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //文件支持类型
            String[] types = config.getImgType().split(",");
            //获取文件类型
            String contentType = file.getContentType();
            boolean flag = false;
            //获取文件后缀
            String suffixName = contentType.substring(contentType.indexOf("/") + 1);
            logger.info("文件后缀: "+suffixName);
            for (String type : types) {
                logger.info("读取到的格式："+type);
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
                book.setImgPath(config.getBaseURL() + config.getDataBasePath()+"/" + imgName);
                return book;
            } else {
                //一个都不匹配
                throw new BusinessException(ReturnCode.IMG_TYPE_ERROR);
            }

    }


}
