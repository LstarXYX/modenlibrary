package modenlibrary.entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录传输类
 * @author L.star
 * @date 2021/3/6 11:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {
    //登录用户名
    private String username;
    //登录密码
    private String password;
    //登录验证码
    private String code;
    //判断验证码的uuid
    private String uuid;
}
