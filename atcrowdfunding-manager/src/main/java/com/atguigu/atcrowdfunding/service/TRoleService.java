package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TRoleService {

    @Autowired
    private TRoleMapper tRoleMapper;


    public List<TRole> getRoleList(String keyWord) {
        if (StringUtil.isNotEmpty(keyWord)){
            TRoleExample tRoleExample = new TRoleExample();
            TRoleExample.Criteria criteria = tRoleExample.createCriteria();
            criteria.andNameLike("%"+keyWord+"%");
            return tRoleMapper.selectByExample(tRoleExample);
        }
        return tRoleMapper.selectByExample(null);
    }

    public int saveTRole(TRole tRole) {
        if (tRole.getId()!=null){
            return tRoleMapper.updateByPrimaryKeySelective(tRole);
        }else {
            return tRoleMapper.insertSelective(tRole);
        }
    }

    public TRole getRoleById(Integer id) {
        return tRoleMapper.selectByPrimaryKey(id);
    }

    public int deleteOne(Integer id) {
        return tRoleMapper.deleteByPrimaryKey(id);
    }

    public int deleteAll(String[] split) {
        TRoleExample tRoleExample = new TRoleExample();
        TRoleExample.Criteria criteria = tRoleExample.createCriteria();
        List<Integer> list = new ArrayList<Integer>();
        for (String s : split) {
            list.add(Integer.parseInt(s));
        }
        criteria.andIdIn(list);
        int i = tRoleMapper.deleteByExample(tRoleExample);
        System.out.println(i);
        return i;
    }
}
