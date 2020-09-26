package com.atguigu.atcrowdfunding.service;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TAdminRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private TAdminMapper tAdminMapper;

    @Autowired
    private TAdminService tAdminService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        tAdminService.
        TAdminExample tAdminExample = new TAdminExample();
        TAdminExample.Criteria criteria = tAdminExample.createCriteria();
        criteria.andLoginacctEqualTo(username);

        List<TAdmin> tAdmins = tAdminMapper.selectByExample(tAdminExample);
        TAdmin tAdmin = null;
        if (tAdmins!=null && tAdmins.size()!=0){
            tAdmin = tAdmins.get(0);
        }else {
            throw new RuntimeException("没有账号信息，请注册");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();

        List<TRole>  roleList = tAdminMapper.selectRolesById(tAdmin.getId());

        List<TPermission> permissionList =  tAdminMapper.selectRoleAuthizationById(tAdmin.getId());

        for (TRole tRole : roleList) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+tRole.getName().trim()));
        }

        for (TPermission tPermission : permissionList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(tPermission.getName()));
        }


        return  new User(tAdmin.getUsername(),tAdmin.getUserpswd(),grantedAuthorities);
    }
}
