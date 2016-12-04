package com.messcat.shisanhang.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.messcat.shisanhang.app.APPApplication;
import com.messcat.shisanhang.app.Constants;

/**
 * Created by Administrator on 2016/10/21.
 */

public class SharedPreferenceUtil {

    private static final String SHAREDPREFERENCES_NAME = "my_sp";

    public static SharedPreferences getAppSp() {
        return APPApplication.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setLoginInformation(String username, String password){
        getAppSp().edit().putString(Constants.SP_USERNAME,username).commit();
        getAppSp().edit().putString(Constants.SP_PASSWORD,password).commit();
    }

    public static String getLoginUsername(){
        return getAppSp().getString(Constants.SP_USERNAME,"");
    }

    public static String getLoginPassword(){
        return getAppSp().getString(Constants.SP_PASSWORD,"");
    }

}
