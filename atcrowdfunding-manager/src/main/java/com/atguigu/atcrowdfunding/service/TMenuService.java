package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.bean.TMenuExample;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TMenuService {

    @Autowired
    private TMenuMapper tMenuMapper;

    public List<TMenu> getFatherTMenuWithSon(){
        List<TMenu> tMenus = tMenuMapper.selectByExample(null);
        List<TMenu> fatherTMenus = new ArrayList<TMenu>();

        for (TMenu tMenu : tMenus) {
            if (tMenu.getPid()==0){
                fatherTMenus.add(tMenu);
            }
        }
        for (TMenu fatherTMenu : fatherTMenus) {
            fatherTMenu.setChildTMenu(new ArrayList<TMenu>());
            for (TMenu tMenu : tMenus) {
                if (tMenu.getPid()!=0){
                    if (fatherTMenu.getId().equals(tMenu.getPid()) ){
                        fatherTMenu.getChildTMenu().add(tMenu);
                    }
                }
            }
        }


        return fatherTMenus;

    }


    public List<TMenu> getList(String keyWord) {


        return null;
    }
}
