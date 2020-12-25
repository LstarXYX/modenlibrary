package modenlibrary.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {
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
    * 图书图片的地址
    */
    private String imgPath;

    private static final long serialVersionUID = 1L;
}