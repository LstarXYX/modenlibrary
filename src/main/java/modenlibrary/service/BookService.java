package modenlibrary.service;

import com.github.pagehelper.PageInfo;
import modenlibrary.Common.eum.BookStatus;
import modenlibrary.Common.vo.PageRequest;
import modenlibrary.entity.Book;
import modenlibrary.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface BookService{


    void deleteByPrimaryKey(String isbn);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(String isbn);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    PageInfo<Book> queryAll(PageRequest pageRequest,Book book);

    void returnedBook(User user, String isbn, BookStatus status);

    void lendBook(User user,String isbn);

    void addBook(Book book, MultipartFile file);

    void updateBook(Book book, MultipartFile file);

    Map<String, Integer> categoryNum();

    void uploadExcel(MultipartFile file);
}
