package com.lego.shiro;

import com.lego.mapper.PermissionMapper;
import com.lego.mapper.RoleMapper;
import com.lego.mapper.UserMapper;
import com.lego.pojo.Permission;
import com.lego.pojo.Role;
import com.lego.pojo.UserInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    PermissionMapper permissionMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        String sUserName = userInfo.getUsername();
        // 自定义Mapper
        List<Role> lstRole = roleMapper.getRoles(sUserName);
        List<Permission> lstPermission = permissionMapper.getPermissions(sUserName);
        // 角色和权限分开查询，也可以改成针对每一种角色查询角色

        System.out.println(lstRole.size());
        for (Role role : lstRole) {
            authorizationInfo.addRole(role.getRole());
            System.out.println(role);
        }
        System.out.println(lstPermission.size());
        for (Permission permission : lstPermission) {
            authorizationInfo.addStringPermission(permission.getPermission());
            System.out.println(permission);
        }
        return authorizationInfo;
    }

    /**
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        // 获取用户的输入的账号密码
        String username = (String) token.getPrincipal();
        System.out.println(token.getCredentials());
        // 通过username从数据库中查找User对象
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userMapper.getUserInfo(username);
        System.out.println("----->>userInfo=" + userInfo);
        if (userInfo == null) {
            return null;
        }
        // 用户名、密码、salt=username+salt、realm name
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),
                getName()
        );
        // 如果登陆失败，会跳转到/login；成功则在return之后就结束登录过程
        return authenticationInfo;
    }

}