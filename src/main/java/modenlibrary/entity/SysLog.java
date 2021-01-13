package modenlibrary.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author  L.star
 * @date 2020/12/26 15:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {
    private Long id;

    /**
    * 用户id
    */
    private Integer uid;

    /**
    * 操作
    */
    private String oper;

    /**
    * 方法名
    */
    private String method;

    /**
    * 参数
    */
    private String params;

    /**
    * 操作时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdate;

    private String ip;

    private static final long serialVersionUID = 1L;
}