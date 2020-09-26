package com.atguigu.atcrowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启注解,并且允许书写 pre  post 方法 AOP模式
@Configuration
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证方法
     * @param auth 身份验证管理器生成器
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        http.authorizeRequests().antMatchers("/index.jsp","/static/**").permitAll();


        http.formLogin().loginPage("/index.jsp");

        http.formLogin().loginProcessingUrl("/login")
                .usernameParameter("loginacct")
                .passwordParameter("userpswd")
                .defaultSuccessUrl("/main");

        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                httpServletRequest.setAttribute("msg",e.getMessage());
                if("XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"))){
//ajax请求
                    String msg = "403";
                    httpServletResponse.getWriter().write(msg);
                }else{
//普通请求
                    httpServletRequest.getRequestDispatcher("/WEB-INF/views/403.jsp").forward(httpServletRequest, httpServletResponse);
                }

            }
        });

        http.csrf().disable();
    }
}
