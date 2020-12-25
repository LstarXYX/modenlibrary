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
public class Blacklist implements Serializable {
    /**
    * 用户的id
    */
    private Integer uid;

    /**
    * 用户名
    */
    private String uname;

    /**
    * 拉入黑名单的日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date cdate;

    private static final long serialVersionUID = 1L;
}