package modenlibrary.mapper;

import modenlibrary.entity.Book;

import java.util.List;

/**
 * @author  L.star
 * @date 2020/12/23 16:08
 */
public interface BookMapper {
    int deleteByPrimaryKey(String isbn);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(String isbn);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);

    List<Book> queryAll(Book book);
}