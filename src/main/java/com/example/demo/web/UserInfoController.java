package com.example.demo.web;

import com.example.demo.repository.UserInfoRepository;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


/**
 * sys_permisssions
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")
    public String userInfo(){
        return "userInfo";
    }

    /**
     * 用户添加;
     * @RequiresRoles({"vip"})无法访问
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")
    //@RequiresRoles({"admin"})
    @RequiresRoles({"vip"})
    public String userInfoAdd(){
        return "userInfoAdd";
    }


    /**
     * 用户删除 目前是没访问权限的，访问会进入403页面。
     * 想要开启访问权限 在sys_role_permission中添加一条数据3（权限ID）	1（角色ID）
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")
    //@ExceptionHandler(UnauthorizedException.class)
    public String userInfoDel() {
        return "userInfoDel";
    }
}
