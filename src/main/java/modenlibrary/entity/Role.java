package modenlibrary.entity;

import java.io.Serializable;
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
public class Role implements Serializable {
    private Integer roleId;

    /**
    * 角色名
    */
    private String roleName;

    /**
    * 角色描述
    */
    private String roleDesc;

    private static final long serialVersionUID = 1L;
}