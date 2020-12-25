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
public class Permission implements Serializable {
    private Integer permissionId;

    /**
    * like:employee:list
    */
    private String permissionName;

    /**
    * 权限描述
    */
    private String permissionDesc;

    /**
    * 资源的url
    */
    private String permissionUrl;

    private static final long serialVersionUID = 1L;
}