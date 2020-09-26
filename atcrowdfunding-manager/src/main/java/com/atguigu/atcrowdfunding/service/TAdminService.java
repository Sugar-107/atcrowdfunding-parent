package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.Data;
import com.atguigu.atcrowdfunding.util.DateUtil;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TAdminService {

    @Autowired
    private TAdminMapper tAdminMapper;

    public TAdmin login(TAdmin  tAdmin) throws Exception {
        TAdminExample tAdminExample = new TAdminExample();
        TAdminExample.Criteria criteria = tAdminExample.createCriteria();
        criteria.andLoginacctEqualTo(tAdmin.getLoginacct());
        criteria.andUserpswdEqualTo(MD5Util.digest(tAdmin.getUserpswd()));
        List<TAdmin> tAdmins = tAdminMapper.selectByExample(tAdminExample);
        if (tAdmins==null || tAdmins.size()==0){
            throw new Exception("用户名或者密码错误!");
        }

        return tAdmins.get(0);
    }

    public List<TAdmin> getList(String keyWord) {
        TAdminExample tAdminExample = new TAdminExample();
        TAdminExample.Criteria criteria1 = tAdminExample.createCriteria();
        criteria1.andUsernameLike("%"+keyWord+"%");

        TAdminExample.Criteria criteria2 = tAdminExample.createCriteria();
        criteria2.andEmailLike("%"+keyWord+"%");

        TAdminExample.Criteria criteria3 = tAdminExample.createCriteria();
        criteria3.andLoginacctLike("%"+keyWord+"%");

        tAdminExample.or(criteria2);
        tAdminExample.or(criteria3);

        List<TAdmin> tAdmins = tAdminMapper.selectByExample(tAdminExample);

        return tAdmins;

    }

    public void saveTadmin(TAdmin tAdmin) {

        tAdmin.setUserpswd( new BCryptPasswordEncoder().encode(Const.DEFALUT_PASSWORD));
        tAdmin.setCreatetime(DateUtil.getFormatTime());
        tAdminMapper.insertSelective(tAdmin);
    }

    public void deleteOne(Integer id) {

        tAdminMapper.deleteByPrimaryKey(id);
    }

    public TAdmin getOne(Integer id) {
        TAdmin tAdmin = tAdminMapper.selectByPrimaryKey(id);
        return tAdmin;
    }

    public void updateOne(TAdmin tAdmin) {
        tAdminMapper.updateByPrimaryKeySelective(tAdmin);
    }

    public void deleteAll(List<Integer> split) {
        TAdminExample tAdminExample = new TAdminExample();
        TAdminExample.Criteria criteria = tAdminExample.createCriteria();
        criteria.andIdIn(split);
        tAdminMapper.deleteByExample(tAdminExample);
    }
}
