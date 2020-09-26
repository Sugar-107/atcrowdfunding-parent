package com.atguigu.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启全局的细粒度方法级别权限控制功能,开启方法注解功能
@EnableWebSecurity
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
/**
 *  authentication：身份验证
 *  authorization：授权
 */

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //Demo 版用户信息
//        super.configure(auth);
//        auth.inMemoryAuthentication()
//                .withUser("zhangsan").password("123").roles("大师")
//                .and()
//                .withUser("lisi").password("123").authorities("罗汉拳","太极拳");
        /**
         * 数据库版用户信息,MD5加密方式
         */
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 授权管理
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


//        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/layui/**","/index.jsp").permitAll()
//                .antMatchers("/level1/**").hasAnyRole("学徒","大师","宗师")
////                .antMatchers("/level2/**").hasAnyRole("大师","宗师")
//                .antMatchers("/level2/**").hasAnyAuthority("二级")
//                .antMatchers("/level3/**").hasAnyRole("宗师")
//
                .anyRequest().authenticated();//剩余资源需要认证，必须放在最后


        http.formLogin().loginPage("/index.jsp");

        http.formLogin().loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/main.html");

        http.csrf().disable();
        http.logout();//如果采用get方式退出用户，必须禁用 csrf

        http.exceptionHandling().accessDeniedPage("/unauth.html");

        /**
         * cookie 记住我
         */
        http.rememberMe();

    }
}
