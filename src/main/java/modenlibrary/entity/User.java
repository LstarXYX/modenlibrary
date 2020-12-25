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
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    /**
    * 性别 0表示女 1表示男
    */
    private Byte gender;

    /**
    * 是否是黑名单用户 默认0为否
    */
    private Byte isblack;

    /**
    * 允许借出的书的数目 默认10本
    */
    private Integer allowLend;

    /**
    * 是否注销该用户
    */
    private Byte isdel;

    /**
    * 用户注册日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registerDate;

    private static final long serialVersionUID = 1L;
}