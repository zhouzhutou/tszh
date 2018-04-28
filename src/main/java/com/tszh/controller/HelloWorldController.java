package com.tszh.controller;

import com.tszh.entity.Address;
import com.tszh.entity.Permission;
import com.tszh.entity.Role;
import com.tszh.entity.User;
import com.tszh.service.HelloWorldService;
import com.tszh.service.RoleService;
import com.tszh.service.UserService;
import com.tszh.util.CryptographyUtil;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/24 0024.
 */
@Controller
public class HelloWorldController {

    @Autowired
    HelloWorldService helloWorldService;

    @Autowired
    RoleService roleService;

    @Autowired
    CryptographyUtil cryptographyUtil;

    @Autowired
    UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public User helloWorld()
    {
        /*Permission p1=new Permission();
        p1.setPermissionName("user:query,add,delete,update:*");
        Permission p2=new Permission();
        p2.setPermissionName("exchangeBook:query,add,delete,update:*");
        Permission p3=new Permission();
        p3.setPermissionName("exchangeItem:query,add,delete,update:*");

        Role adminRole=new Role();
        adminRole.setRoleName("admin");

        Set<Permission> permissionSet=new HashSet<>();
        permissionSet.add(p1);
        permissionSet.add(p2);
        permissionSet.add(p3);
        adminRole.setPermissions(permissionSet);//为admin设置权限
        roleService.save(adminRole);//保存角色

        Address address=new Address();
        address.setCity("成都市");
        address.setCounty("高新西区");
        address.setProvince("四川省");
        address.setStreetAddress("电子科技大学清水河校区");

        //设置admin信息
        User admin=new User();
        admin.setUsername("zhouzhutou");
        admin.setEmail("1394854927@qq.com");
        admin.setPassword(cryptographyUtil.md5("123456"));
        admin.setDeposit(999999);

        admin.setAddress(address);//为admin设置地址
        admin.setRole(adminRole);//为admin设置角色

        userService.save(admin);
        return admin;*/

        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        User user=new User();
        user.setUsername("shepherd_1");
        String email="2396621573@qq.com";
        user.setEmail("2396621573@qq.com");
        user.setSalt(randomNumberGenerator.nextBytes().toString());
        String newPassword=cryptographyUtil.md5("123456",user.getSalt());
        user.setPassword(newPassword);
        user.setDeposit(100);

        Address address=new Address();
        address.setCity("成都市");
        address.setCounty("邛崃市");
        address.setProvince("四川省");
        address.setStreetAddress("玉带街66号");

        Permission p1=new Permission();
        p1.setPermissionName("user:query,update:"+email);
        Permission p2=new Permission();
        p2.setPermissionName("exchangeBook:query,add,delete,update:"+email);
        Permission p3=new Permission();
        p3.setPermissionName("exchangeItem:query,add,delete,update:"+email);
        Role userRole=new Role();
        Set<Permission> permissionSet=new HashSet<>();
        permissionSet.add(p1);
        permissionSet.add(p2);
        permissionSet.add(p3);
        userRole.setPermissions(permissionSet);
        roleService.save(userRole);

        user.setRole(userRole);
        user.setAddress(address);
        userService.save(user);
        return user;

    }
}
