package modenlibrary.Common.vo;

import lombok.Data;

/**
 * 分页请求
 * @author L.star
 * @date 2020/12/23 17:53
 */
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum = 0;
    /**
     * 每页数量 默认20
     */
    private int pageSize = 20;

}
