package com.messcat.shisanhang.util;

import android.util.Log;

/**
 * Created by Mander on 2016/10/24.
 */

public class LogUtil {

    public static final String EMPTY_TAG = "emptyTag";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String PAY = "pay";
    public static final String PERSONAL_INFO = "personalInfo";
    public static final String COLLECTION = "collection";
    public static final String WITHDRAW = "withdraw";
    public static final String OPEN_SHOP = "openShop";
    public static final String MODIFY_PASS = "modifyPass";
    public static final String LIVE = "live";

    public static void e(String tag, String text) {
        if (tag.equals(LIVE)) {
            Log.e(tag, text);
        }
    }
}
