package com.example.ooo.backend.util;

import java.util.ArrayList;
import java.util.List;

public final class Constants {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final String REDIRECT_ADMIN = "redirect:/admin";
    public static final String REDIRECT_USER = "redirect:/user";

    public static List<String> REDIRECT_LIST() {
        List<String> list = new ArrayList<>();
        list.add(REDIRECT_ADMIN);
        list.add(REDIRECT_USER);
        return list;
    }


}
