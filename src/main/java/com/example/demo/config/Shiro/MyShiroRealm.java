package com.example.demo.config.Shiro;

import com.example.demo.entity.SysPermission;
import com.example.demo.entity.SysRole;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;


public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private UserInfoService userInfoService;


    /**
     * 认证（登录）
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("开始身份验证");
        String username = (String) token.getPrincipal();

        UserInfo userInfo = userInfoService.findByUsername(username);
//        String passworaaa= new Md5Hash("123456",userInfo.getCredentialsSalt(),2).toHex();
//        System.out.println(passworaaa);

        if (userInfo == null) {
            //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户信息
                userInfo.getPassword(), //密码
                getName() //realm name---当前realm对象的name，调用父类的getName()方法即可
        );
        /**
         * 当两个用户的密码相同时，单纯使用不加盐的MD5加密方式，会发现数据库中存在相同结构的密码，
         * 这样也是不安全的。我们希望即便是两个人的原始密码一样，加密后的结果也不一样 所以加盐 就不一样了 类似炒菜
         * 对于盐值credentialsSalt，在Shiro中为org.apache.shiro.util.ByteSource对象：
         * ByteSource credentialsSalt = ByteSource.Util.bytes("");
         * ByteSource提供了一个内部方法，可以将字符串转换为对应的盐值信息。一般情况下我们使用一个
         * 唯一的字符串作为盐值。在本测试样例中，我们使用用户名作为盐的原始值
         */
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userInfo.getCredentialsSalt())); //设置盐

        return authenticationInfo;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        System.out.println("开始权限配置");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();

        for (SysRole role : userInfo.getRoleList()) {//遍历当前用户所拥有的角色
            authorizationInfo.addRole(role.getRole());//设置当前用户角色
            for (SysPermission p : role.getPermissions()) {//遍历角色所对应的权限
                authorizationInfo.addStringPermission(p.getPermission());//设置当前用户（用户所关联角色)所对应的权限
            }
        }

        return authorizationInfo;
    }
}
