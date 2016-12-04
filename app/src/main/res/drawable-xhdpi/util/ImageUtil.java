package com.messcat.shisanhang.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Mander on 2016/10/24.
 */

public class ImageUtil {

    // 获取图片的RequestCode
    public static final int GET_PHOTO = 0x100;
    // 裁剪图片的RequestCode
    public static final int TAILOR = 0x200;

    /**
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从图库里面拿图片
     */
    public static void getPhotoFromPhotoGallery(Activity context) { // 获取图库图片的方法
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        context.startActivityForResult(intent, GET_PHOTO);
    }

    /**
     * 从图库里面拿图片
     */
    public static void getPhotoFromPhotoGallery(Activity context, int requestCode) { // 获取图库图片的方法
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 裁剪从相册中获取到的图片
     */
    public static void tailor(Activity context, Intent intent) {
        if (intent == null) {
            return;
        }
        Uri uri = intent.getData();
        /**
         * 跳转到裁剪界面
         */
        Intent intentTailor = new Intent(); // 跳到裁剪图片界面的intent
        intentTailor.setAction("com.android.camera.action.CROP");
        intentTailor.setDataAndType(uri, "image/*");// Uri是已经选择的图片Uri
        intentTailor.putExtra("crop", "true");
        intentTailor.putExtra("aspectX", 1);// 裁剪框比例
        intentTailor.putExtra("aspectY", 1);
        intentTailor.putExtra("outputX", 150);// 输出图片大小
        intentTailor.putExtra("outputY", 150);
        intentTailor.putExtra("return-data", true);
        context.startActivityForResult(intentTailor, TAILOR);
    }

    /**
     * 裁剪从相册中获取到的图片
     */
    public static void tailor(Activity context, Intent intent, int requestCode) {
        if (intent == null) {
            return;
        }
        Uri uri = intent.getData();
        /**
         * 跳转到裁剪界面
         */
        Intent intentTailor = new Intent(); // 跳到裁剪图片界面的intent
        intentTailor.setAction("com.android.camera.action.CROP");
        intentTailor.setDataAndType(uri, "image/*");// Uri是已经选择的图片Uri
        intentTailor.putExtra("crop", "true");
        intentTailor.putExtra("aspectX", 1);// 裁剪框比例
        intentTailor.putExtra("aspectY", 1);
        intentTailor.putExtra("outputX", 150);// 输出图片大小
        intentTailor.putExtra("outputY", 150);
        intentTailor.putExtra("return-data", true);
        context.startActivityForResult(intentTailor, requestCode);
    }

    public static void glideDisplay(Context context, String path, int defaultImage, final ImageView imageView) {
        Glide.with(context)
                .load(path)
                .placeholder(context.getResources().getDrawable(defaultImage))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
