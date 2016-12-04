package com.messcat.shisanhang.util;

import android.widget.Toast;

import com.messcat.shisanhang.app.APPApplication;

/**
 * Created by Mander on 2016/10/24.
 */

public class ToastUtil {

    public static void showShort(String TsMessage) {
        Toast.makeText(APPApplication.getInstance(), TsMessage, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String TsMessage) {
        Toast.makeText(APPApplication.getInstance(), TsMessage, Toast.LENGTH_SHORT).show();
    }

    public static void showLoadNoMore() {
        showShort("没有更多的数据了");
    }

}
