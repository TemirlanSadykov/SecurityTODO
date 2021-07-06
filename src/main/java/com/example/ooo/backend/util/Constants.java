package com.example.ooo.backend.util;

import java.util.ArrayList;
import java.util.List;

public final class Constants {

    public static final String SENDER = "tester2004tester2004@gmail.com";
    public static final String SENDER_PASSWORD = "qwerty2004password";

    public static final String HOST = "mail.smtp.host";
    public static final String HOST_VALUE = "smtp.gmail.com";
    public static final String PORT = "mail.smtp.port";
    public static final String PORT_VALUE = "587";
    public static final String AUTH = "mail.smtp.auth";
    public static final String AUTH_VALUE = "true";
    public static final String SSL = "mail.smtp.ssl.trust";
    public static final String SSL_VALUE = "smtp.gmail.com";
    public static final String STARTTLS = "mail.smtp.starttls.enable";
    public static final String STARTTLS_VALUE = "true";

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    public static final String LINK_ADMIN = "admin";
    public static final String LINK_USER = "user";

    public static final String REDIRECT_ADMIN = "redirect:/admin";
    public static final String REDIRECT_USER = "redirect:/user";

    public static List<String> REDIRECT_LIST() {
        List<String> list = new ArrayList<>();
        list.add(REDIRECT_ADMIN);
        list.add(REDIRECT_USER);
        return list;
    }


}
