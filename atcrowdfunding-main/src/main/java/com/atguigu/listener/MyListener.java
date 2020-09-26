package com.atguigu.listener;

import com.atguigu.atcrowdfunding.util.Const;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String ContextPath = servletContext.getContextPath();
        servletContext.setAttribute(Const.PATH,ContextPath);
        servletContext.setAttribute("commJsp",Const.commJsp);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
