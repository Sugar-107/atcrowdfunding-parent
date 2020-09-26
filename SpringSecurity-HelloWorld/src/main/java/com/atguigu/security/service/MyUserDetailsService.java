package com.atguigu.security.service;

import com.atguigu.security.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

//    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Map<String, Object> map = jdbcTemplate.queryForMap("select  * from t_admin where loginacct = ?", username);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        List<Map<String, Object>> roleList = jdbcTemplate.query("" +
                "SELECT name \n" +
                "from t_role \n" +
                "INNER JOIN t_admin_role \n" +
                "on t_admin_role.roleid=t_role.id \n" +
                "WHERE t_admin_role.adminid = ?", new ColumnMapRowMapper(), map.get("id"));

        List<Map<String, Object>> authList = jdbcTemplate.query("\n" +
                "SELECT name \n" +
                "FROM t_permission \n" +
                "INNER JOIN t_role_permission \n" +
                "ON t_permission.id = t_role_permission.permissionid \n" +
                "INNER JOIN t_admin_role \n" +
                "ON t_admin_role.roleid = t_role_permission.roleid \n" +
                "WHERE t_admin_role.adminid = ?;", new ColumnMapRowMapper(), map.get("id"));

        //添加角色
        for (Map<String, Object> stringObjectMap : roleList) {

            if (StringUtil.isNotEmpty(stringObjectMap.get("name").toString())){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+stringObjectMap.get("name").toString()));
            }
        }

        //角色添加权限
        for (Map<String, Object> stringObjectMap : authList) {
            if (StringUtil.isNotEmpty(stringObjectMap.get("name").toString())){
                authorities.add(new SimpleGrantedAuthority(stringObjectMap.get("name").toString()));
            }
        }

        return new User(map.get("loginacct").toString(),map.get("userpswd").toString(), authorities);
    }
}
