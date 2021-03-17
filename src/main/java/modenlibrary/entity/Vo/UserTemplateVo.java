package modenlibrary.entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import modenlibrary.entity.User;

import java.io.Serializable;
import java.util.Date;

/**
 * 批量添加用户模板映射
 * @author L.star
 * @date 2021/3/17 21:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTemplateVo implements Serializable {
    //用户名 密码一致
    private String username;
    //性别 0 女 1男
    private String gender;

    public User convert(){
        byte g = (byte) ("男".equals(this.gender)?1:0);
        User user = User.builder().username(this.username).password(this.username)
                .gender(g).registerDate(new Date()).build();
        return user;
    }

}
