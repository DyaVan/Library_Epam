package com.diachuk.library.controller;

import com.diachuk.library.manage.LibraryConfig;
import com.diachuk.library.services.BackgroundService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by VA-N_ on 11.01.2017.
 */
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LibraryConfig.getInstance().fillCommandsMapping();
        LibraryConfig.getInstance().refreshLibraryConfig();
        BackgroundService.getInstance().StartBackgroundProcesses();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("dasd");
        BackgroundService.getInstance().shutdown();
    }
}
