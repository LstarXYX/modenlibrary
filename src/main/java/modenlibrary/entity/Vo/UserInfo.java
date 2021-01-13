package modenlibrary.entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modenlibrary.entity.Role;
import modenlibrary.entity.User;

/**
 * @author L.star
 * @date 2021/1/6 14:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends User {
    private Role role;
}
