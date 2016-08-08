package com.spotlight.swagger;

import com.wordnik.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class Bootstrap extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setBasePath("https://local.leapset.com:8443/api");
        beanConfig.setResourcePackage("com.spotlight.api");
        beanConfig.setScan(true);
    }
}