package modenlibrary.mapper;

import modenlibrary.entity.Category;

/**
 * @author L.star
 * @date 2020/12/23 20:48
 */
public interface CategoryMapper {
    Category findById(Integer id);
}
