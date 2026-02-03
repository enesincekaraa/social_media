package com.enesincekara.config;

public class RestApis {

    public static final String DEVELOPER = "/dev";
    public static final String TEST = "/test";
    public static final String RELEASE = "/prod";
    public static final String VERSIONS = "/v1";
    public static final String USERSERVICE =DEVELOPER+VERSIONS+"/user";
    public static final String CREATE = "/create-profile";
    public static final String UPDATE = "/update-profile";
    public static final String GETPROFILE = "/get-profile";
    public static final String DELETE = "/delete-profile";
    public static final String CHANGE = "/change-password";
    public static final String CONTACT = "/contact-info/{authId}";
    public static final String UPDATE_DETAILS ="update-details";

}
