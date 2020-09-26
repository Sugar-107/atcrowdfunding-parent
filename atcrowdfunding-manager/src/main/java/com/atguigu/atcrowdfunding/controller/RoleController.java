package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private TRoleService tRoleService;

    /**
     * 获取分页信息包括分页结果
     * @param pageNum 当前页
     * @param pageSize 每页个数
     * @param keyWord 模糊查询关键字
     * @return 用pageInfo包装的分页结果
     */
    @ResponseBody
    @RequestMapping("/role/loadData")
    public PageInfo<TRole> index(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "2") Integer pageSize,
            @RequestParam(value = "keyWord",required = false,defaultValue = "") String keyWord
    ){
        PageHelper.startPage(pageNum,pageSize);
        List<TRole> list = tRoleService.getRoleList(keyWord);
        PageInfo<TRole> pageInfo = new PageInfo<TRole>(list,5);
        return pageInfo;
    }

    /**
     *
     * @return 跳转到首页
     */
    @RequestMapping("/role/index")
    public  String index(){
        return "role/index";
    }


    @ResponseBody
    @RequestMapping("/role/saveRole")
    public String saveRole(TRole tRole){
        int i =  tRoleService.saveTRole(tRole);
        if (i>0){
            return "true";
        }else {
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping("/role/getOne")
    public TRole getOne(Integer id){
        return tRoleService.getRoleById(id);
    }

    @ResponseBody
    @RequestMapping("/role/deleteOne")
    public String deleteOne(Integer id){
        int i =  tRoleService.deleteOne(id);
        System.out.println(i);
        if (i >0){
            return "true";
        }else return "false";
    }

    @ResponseBody
    @RequestMapping("/role/deleteAll")
    public String deleteAll(String ids){
        System.out.println("ids = " + ids);
        String[] split = ids.split(",");
        int i = tRoleService.deleteAll(split);
        if (i>0){
            return "true";
        }else
        return "false";
    }


}
