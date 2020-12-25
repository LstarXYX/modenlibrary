package modenlibrary.controller;

import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.eum.BookStatus;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.Common.utils.PageUtils;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.Common.vo.PageResult;
import modenlibrary.Common.vo.ResultVo;
import modenlibrary.entity.Book;
import modenlibrary.entity.LendList;
import modenlibrary.entity.User;
import modenlibrary.service.BookService;
import modenlibrary.service.LendListService;
import modenlibrary.service.UserService;
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
    public PageResult queryAll(PageRequest pageRequest, Book book){
        return PageUtils.getPageResult(pageRequest,bookService.queryAll(pageRequest,book));
    }

    /**
     * 借阅图书
     * @param isbn
     * @param session
     * @return
     */
    @GetMapping("/lend/{isbn}")
    @ResponseBody
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
    @GetMapping("/return/{isbn}")
    @ResponseBody
    public ResultVo returned(@PathVariable("isbn")String isbn,HttpSession session){
        User user = (User) session.getAttribute("userInfo");
        boolean ok = bookService.returnedBook(user, isbn,BookStatus.RETURNED);
        if (ok){
            return Result.success(user);
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
    @PostMapping("/add")
    @ResponseBody
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
    @PostMapping("/update")
    @ResponseBody
    public ResultVo update(Book book,MultipartFile file){
        book = bookService.updateBook(book,file);
        return Result.success(book);
    }


}
