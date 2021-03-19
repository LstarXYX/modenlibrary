package modenlibrary.entity.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.entity.Book;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Excel批量添加图书映射的类
 * @author L.star
 * @date 2021/3/18 21:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksVo implements Serializable {
    /**
     * 图书的isbn号 总共11位
     */
    private String isbn;

    /**
     * 书名 不能为空
     */
    private String title;

    /**
     * 作者名 不能为空
     */
    private String author;

    /**
     * 图书的出版日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    /**
     * 图书类型的id
     */
    private Integer categoryId;

    /**
     * 该图书的价格
     */
    private BigDecimal price;

    /**
     * 图书的简介
     */
    private String description;

    /**
     * 书本的数量 默认1
     */
    private Integer bookNum;

    /**
     * 出版社
     */
    private String publisher;

    public Book convert(){
        Book book = new Book();
        book.setIsbn(this.isbn);
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setPublishDate(this.publishDate);
        book.setCategoryId( (this.categoryId!=null && this.categoryId!=0)?this.categoryId:1);
        book.setPrice(this.price.intValue()<0?new BigDecimal(99):this.price);
        book.setDescription(this.description);
        book.setBookNum(this.bookNum<0?0:this.bookNum);
        book.setPublisher(this.publisher);
        return book;
    }

    private static final long serialVersionUID = 1L;
}
