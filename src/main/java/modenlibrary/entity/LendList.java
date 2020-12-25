package modenlibrary.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LendList implements Serializable {
    /**
    * 图书isbn
    */
    private String isbn;

    /**
    * 书名
    */
    private String title;

    /**
    * 借书人id
    */
    private Integer uid;

    /**
    * 借出日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lendDate;

    /**
    * 归还日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;

    /**
    * 书本状态 借出 归还或其他
    */
    private String status;

    private static final long serialVersionUID = 1L;
}