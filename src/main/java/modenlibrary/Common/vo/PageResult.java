package modenlibrary.Common.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页返回结果
 * @author L.star
 * @date 2020/12/23 17:55
 */
@Data
public class PageResult {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 数据模型
     */
    private List<?> content;
}
