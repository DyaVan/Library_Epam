package com.diachuk.library.manage;

import java.util.ResourceBundle;

/**
 * Created by VA-N_ on 06.01.2017.
 */
public class NavigationManager {

    private static final String BUNDLE_NAME = "navigation";

    public static final String ERROR_PAGE = "error_page";
    public static final String MAIN_PAGE = "main_page";
    public static final String TOP_10_PAGE = "top_10_page";
    public static final String MY_SPACE_PAGE = "my_space_page";
    public static final String LOGIN_PAGE = "logIn_page";
    public static final String REGISTRATION_PAGE = "registration_page";
    public static final String SPECIFIC_BOOK_PAGE = "specific_book_page";
    public static final String ISSUE_BOOK_PAGE = "issue_book_page";
    public static final String RETURN_BOOK_PAGE = "return_book_page";
    public static final String WORK_SPACE_PAGE = "work_space_page";


    private static NavigationManager instance;
    private ResourceBundle resource;

    private NavigationManager() {
        resource = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public static NavigationManager getInstance(){
        return instance == null ? instance = new NavigationManager() : instance;
    }

    public String getPage(String pageKey){
        return resource.getObject(pageKey).toString();
    }
}
