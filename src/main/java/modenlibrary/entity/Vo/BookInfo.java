package modenlibrary.entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modenlibrary.entity.Book;
import modenlibrary.entity.Category;

/**
 * @author L.star
 * @date 2020/12/23 20:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo extends Book {
    //分类名
    private String cname;
}
