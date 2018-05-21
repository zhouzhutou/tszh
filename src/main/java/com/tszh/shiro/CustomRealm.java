package com.tszh.shiro;


import com.tszh.entity.Permission;
import com.tszh.entity.Role;
import com.tszh.entity.User;
import com.tszh.service.UserService;
import com.tszh.util.CryptographyUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
@Component("customRealm")
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    CryptographyUtil cryptographyUtil;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        String email=(String)principalCollection.getPrimaryPrincipal();
        Role role=userService.getRoleByEmail(email);
        System.out.println("role: "+role.getRoleName());
        simpleAuthorizationInfo.addRole(role.getRoleName());
       /* Set<Permission> permissions=role.getPermissions();
        Set<String> permissList=new HashSet<>();
        System.out.println("permission: ");
        for(Permission perm:permissions) {
            System.out.print(perm.getPermissionName());
            permissList.add(perm.getPermissionName());
        }
        System.out.println();
        simpleAuthorizationInfo.addStringPermissions(permissList);*/
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String email=(String)authenticationToken.getPrincipal();
        //HashedCredentialsMatcher m;
        User user=userService.findUserByEmail(email);
        if(user==null)
            throw new UnknownAccountException();
            //SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user.getEmail(),user.getPassword(),
            //        ByteSource.Util.bytes(salt),getName());
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),getName());
        return simpleAuthenticationInfo;
    }
}
