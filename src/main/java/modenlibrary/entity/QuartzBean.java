package modenlibrary.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author L.star
 * @date 2021/5/17 12:21
 */
@Data
@ToString
public class QuartzBean {
    /**
     * 任务id
     */
    private Integer id;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务执行类
     */
    private String jobClass;
    /**
     * 任务状态：启动、暂停
     */
    private Integer status;
    /**
     * 任务运行时间的表达式
     */
    private String cronExpression;
}
