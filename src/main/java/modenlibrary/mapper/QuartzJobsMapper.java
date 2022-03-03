package modenlibrary.mapper;

import modenlibrary.entity.QuartzBean;

import java.util.List;

/**
 * @author L.star
 * @date 2021/5/17 13:09
 */
public interface QuartzJobsMapper {

    int insert(QuartzBean quartzBean);

    int update(QuartzBean quartzBean);

    int deleteById(Integer id);

    QuartzBean queryById(Integer id);

    List<QuartzBean> queryAll(QuartzBean quartzBean);

}
