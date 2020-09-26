package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.service.TMenuService;
import com.atguigu.atcrowdfunding.util.Const;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TAdminController {


    @Autowired
    private TAdminService tAdminService;

    @Autowired
    private TMenuService tMenuService;

/*    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public String login(TAdmin Tadmin, HttpSession httpSession){
        try {
            TAdmin login = tAdminService.login(Tadmin);
            httpSession.setAttribute("tAdmin",login);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            httpSession.setAttribute("erro",e.getMessage());
            return "redirect:/index.jsp";
        }
        return "redirect:/main";
    }*/

    @RequestMapping("/main")
    public String mian(HttpSession session){
        List<TMenu> fatherTMenuWithSon = tMenuService.getFatherTMenuWithSon();
        session.setAttribute("fatherTMenu",fatherTMenuWithSon);
        return "main";
    }

    @PreAuthorize("hasAnyRole('?')")
    @RequestMapping("/admin/index")
    public String mainIndex(
            @RequestParam(value = "pageNum",required = false,defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "2") Integer pageSize,
            @RequestParam(value = "keyWord",required = false,defaultValue = "") String keyWord,
            HttpSession session
            ){
        PageHelper.startPage(pageNum,pageSize);
        List<TAdmin> list =  tAdminService.getList(keyWord);
        PageInfo<TAdmin> pageInfo = new PageInfo<TAdmin>(list,5);
        session.setAttribute("pageInfo",pageInfo);

        return "admin/Admin";
    }

    @RequestMapping("/toadd")
    public String toAdd(){
        return "admin/add";
    }

    @RequestMapping("/admin/saveTadmin")
    public String saveTadmin(TAdmin tAdmin){
        tAdminService.saveTadmin(tAdmin);
        return "forward:/admin/index?pageNum="+Integer.MAX_VALUE;
    }



    @RequestMapping("/delete")
    public String deleteOne(Integer id,Integer pageNum){
        tAdminService.deleteOne(id);
        return "forward:/admin/index?pageNum="+pageNum;
    }


    @RequestMapping("/update")
    public String updateOne(Integer id,Integer pageNum,Map<String,Object> map){
        TAdmin tAdmin = tAdminService.getOne(id);
        map.put("tAdmin",tAdmin);
        return "/admin/edit";
    }


    @RequestMapping("toUpdate")
    public String toUpdate(TAdmin tAdmin,Integer pageNum){
        System.out.println(pageNum);
        tAdminService.updateOne(tAdmin);
        return "forward:/admin/index?pageNum="+pageNum;
    }

    @RequestMapping("/admin/delAll")
    public String delAll(String ids){
        String[] split = ids.split(",");
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        tAdminService.deleteAll(list);
        return "forward:/admin/index";
    }

/*    @RequestMapping("/admin/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("tAdmin");
        return "redirect:/index.jsp";
    }*/


}
