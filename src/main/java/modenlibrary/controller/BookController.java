package modenlibrary.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.eum.BookStatus;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.PageUtils;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.Common.vo.PageResult;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.anno.Operation;
import modenlibrary.entity.Book;
import modenlibrary.entity.LendList;
import modenlibrary.entity.User;
import modenlibrary.service.BookService;
import modenlibrary.service.LendListService;
import modenlibrary.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 图书控制层
 * @author L.star
 * @date 2020/12/23 20:23
 */
@Controller
@RequestMapping("/book")
@CrossOrigin(originPatterns = "*",maxAge = 3600)
@Slf4j
public class BookController {
    private Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private LendListService lendListService;

    /**
     * 查询所有图书 可以附加条件查询
     * @param pageRequest
     * @param book
     * @return
     */
    @GetMapping("/queryAll")
    @ResponseBody
    @RequiresPermissions("querybook")
    public PageResult queryAll(PageRequest pageRequest, Book book){
        return PageUtils.getPageResult(pageRequest,bookService.queryAll(pageRequest,book));
    }

    /**
     * 借阅图书
     * @param isbn
     * @param session
     * @return
     */
    @Operation("/借阅图书")
    @GetMapping("/lend/{isbn}")
    @ResponseBody
    @RequiresPermissions("lend")
    public ResultVo lend(@PathVariable("isbn")String isbn, HttpSession session){
        //获取用户
        User user = (User) session.getAttribute("userInfo");
        bookService.lendBook(user,isbn);
        session.setAttribute("userInfo",user);
        return Result.success(user);
    }

    /**
     * 归还图书
     *
     * @param isbn
     * @param session
     * @return
     */
    @Operation("/改变借阅的状态")
    @GetMapping("/status/isbn/{isbn}/uid/{uid}/to/{status}")
    @ResponseBody
    @RequiresPermissions("return")
    public ResultVo returned(@PathVariable("isbn")String isbn,@PathVariable("uid")Integer uid,@PathVariable("status")String status,HttpSession session){
        //判断用户是否存在
        User user = userService.selectByPrimaryKey(uid);
        if (user==null){
            throw new BusinessException(ReturnCode.NOT_USER);
        }
        //判断状态
        if (StrUtil.isEmptyIfStr(status)){
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        if (BookStatus.LENDING.getMsg().equals(status)){
            //借出
            boolean ok = bookService.returnedBook(user, isbn,BookStatus.LENDING);
            if (ok){
                return Result.success(user);
            }
        }else if (BookStatus.RETURNED.getMsg().equals(status)){
            //归还
            boolean ok = bookService.returnedBook(user, isbn,BookStatus.RETURNED);
            if (ok){
                return Result.success(user);
            }
        }else if (BookStatus.OTHERS.getMsg().equals(status)){
//            其他
            boolean ok = bookService.returnedBook(user, isbn,BookStatus.OTHERS);
            if (ok){
                return Result.success(user);
            }
        }else {
            throw new BusinessException(ReturnCode.FORM_ERROR);
        }
        return Result.fail(ReturnCode.SYSTEM_ERROR);
    }

    /**
     * 添加图书 返回图书信息
     *
     * @param book 必选 isbn、title、author、publishDate
     * @param file 上传图片
     * @return
     */
    @Operation("/添加图书")
    @PostMapping("/add")
    @ResponseBody
    @RequiresPermissions("addbook")
    public ResultVo add(Book book, MultipartFile file){
        book = bookService.addBook(book,file);
        return Result.success(book);
    }

    /**
     * 更新图书信息
     *
     * @param book
     * @param file
     * @return
     */
    @Operation("/更新图书")
    @PostMapping("/update")
    @ResponseBody
    @RequiresPermissions("updatebook")
    public ResultVo update(Book book,MultipartFile file){
        book = bookService.updateBook(book,file);
        return Result.success(book);
    }

    /**
     * 超级管理员对图书进行删除
     *
     * @param isbn
     * @return
     */
    @Operation("/删除图书")
    @GetMapping("/del/{isbn}")
    @ResponseBody
    @RequiresRoles("超级管理员")
    public ResultVo del(@PathVariable("isbn")String isbn){
        if (isbn.length()!=10){
            return Result.fail(ReturnCode.BOOK_UNKNOWN);
        }
        int ok = bookService.deleteByPrimaryKey(isbn);
        if (ok==1){
            return Result.success("删除成功");
        }else {
            return Result.fail(ReturnCode.SYSTEM_ERROR);
        }
    }


    /**
     * 提供下载Excel模板文件
     *
     * @param response
     */
    @GetMapping("/excel")
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    public void getExcelTemplate(HttpServletResponse response){
        ExcelWriter writer = ExcelUtil.getWriter();
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("isbn", "ISBN号 需唯一 必填");
        row.put("title","书名 必填");
        row.put("author","作者 必填");
        row.put("publishDate","出版日期 必填 格式：yyyy-MM-dd");
        row.put("categoryId","分类id");
        row.put("price","价格");
        row.put("description","描述");
        row.put("bookNum","数量");
        row.put("publisher","出版社");
        row.put("img","书本图片");
        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows,true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition","attachment;filename=Template.xls");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            writer.flush(outputStream,true);
            //close
            writer.close();
            IoUtil.close(outputStream);
        } catch (IOException e) {
            log.error("Excel下载错误!");
        }finally {
            writer.close();
            if (outputStream!=null){
                IoUtil.close(outputStream);
            }
        }
    }

    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/excel")
    @RequiresRoles(value = {"超级管理员","普通管理员"},logical = Logical.OR)
    @Operation("上传了图书Excel表")
    public ResultVo uploadExcel(MultipartFile file){
        bookService.uploadExcel(file);
        return Result.success("Success");
    }

}
