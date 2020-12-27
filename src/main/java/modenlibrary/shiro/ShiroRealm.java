package modenlibrary.shiro;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.exception.BusinessException;
import modenlibrary.entity.Permission;
import modenlibrary.entity.Role;
import modenlibrary.entity.User;
import modenlibrary.service.PermissionService;
import modenlibrary.service.RoleService;
import modenlibrary.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author L.star
 * @date 2020/12/27 15:05
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user= (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles=roleService.getRolesByUserId(user.getId());
        List<Permission> permissions=permissionService.getPermissionsByUserId(user.getId());
        simpleAuthorizationInfo.addRoles(roles.stream().map(Role::getRoleName).collect(Collectors.toSet()));
        simpleAuthorizationInfo.addStringPermissions(permissions.stream().map(Permission::getPermissionName).collect(Collectors.toSet()));
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("开始登录认证");
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.selectByUserName(username);
        log.info(user.getUsername()+"---"+user.getId());
        if (user == null){
            throw new BusinessException(ReturnCode.LOGIN_ERROR);
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user,user.getPassword(),getName()
        );
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userInfo",user);
        return simpleAuthenticationInfo;
    }
}
