package modenlibrary.service;

import com.github.pagehelper.PageInfo;
import modenlibrary.Common.eum.BookStatus;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.entity.Book;
import modenlibrary.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface BookService{


    int deleteByPrimaryKey(String isbn);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(String isbn);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    PageInfo<Book> queryAll(PageRequest pageRequest,Book book);

    Boolean returnedBook(User user, String isbn, BookStatus status);

    void lendBook(User user,String isbn);

    Book addBook(Book book, MultipartFile file);

    Book updateBook(Book book, MultipartFile file);
}
