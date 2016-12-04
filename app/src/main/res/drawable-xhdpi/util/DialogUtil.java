package com.messcat.shisanhang.util;

import android.app.Activity;
import android.content.DialogInterface;

import com.messcat.shisanhang.widget.ErrorDialog;
import com.messcat.shisanhang.widget.MyDialog;

/**
 * Created by os on 2016/10/24.
 */

public class DialogUtil {

    public static void showDialog(Activity context, String text) {
        final ErrorDialog mErrorDialog = new ErrorDialog(context);
        mErrorDialog.setContant(text);
        mErrorDialog.setComplete("确定");
        mErrorDialog.setCancelGone(true);
        mErrorDialog.Listener(new MyDialog.Listener() {
            @Override
            public void complete() {
                mErrorDialog.dismiss();
            }

            @Override
            public void cancel() {
                mErrorDialog.dismiss();
            }
        });
        mErrorDialog.show();
    }

    public static void showDialog(Activity context, String text, DialogInterface.OnDismissListener l) {
        final ErrorDialog mErrorDialog = new ErrorDialog(context);
        mErrorDialog.setContant(text);
        mErrorDialog.setComplete("确定");
        mErrorDialog.setCancelGone(true);
        mErrorDialog.Listener(new MyDialog.Listener() {
            @Override
            public void complete() {
                mErrorDialog.dismiss();
            }

            @Override
            public void cancel() {
                mErrorDialog.dismiss();
            }
        });
        mErrorDialog.setOnDismissListener(l);
        mErrorDialog.show();
    }
}
