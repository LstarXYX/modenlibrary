package modenlibrary.controller;

import cn.hutool.core.util.StrUtil;
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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 图书控制层
 * @author L.star
 * @date 2020/12/23 20:23
 */
@Controller
@RequestMapping("/book")
@CrossOrigin(originPatterns = "*",maxAge = 3600)
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


}
