package com.messcat.shisanhang.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Wallace on 2016/4/12.
 */
public class FlowToDelete {
    private static float downX;
    private static boolean isOpen = false;
    private static boolean result;
    private static int width;

    public static void flowtodelete(final View topView, final View bottomView) {
        //最好的方法 -- 在oncreat里测量控件
        bottomView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = bottomView.getWidth();
                bottomView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        topView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) motionEvent.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) (motionEvent.getRawX() - downX);
                        if (isOpen) {
                            // 打开状态
                            // 向右滑动
                            if (dx > 0 && dx < width) {
                                view.setTranslationX(dx - width);
                                // 允许移动，阻止点击
                                result = true;
                            }
                        } else {
                            // 闭合状态
                            // 向左移动
                            if (dx < 0 && Math.abs(dx) < width) {
                                view.setTranslationX(dx);
                                // 允许移动，阻止点击
                                result = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:

                        // Log.i("", "ACTION_UP" + v.getTranslationX());

                        // 获取已经移动的
                        float ddx = view.getTranslationX();

                        // 判断打开还是关闭

                        if (ddx <= 0 && ddx > -(width / 2)) {
                            // 关闭
                            ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "translationX", ddx, 0).setDuration(100);
                            oa1.start();
                            oa1.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    isOpen = false;
                                    result = false;
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                    isOpen = false;
                                    result = false;
                                }
                            });
                        }
                        if (ddx <= -(width / 2) && ddx > -width) {
                            // 打开
                            ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "translationX", ddx, -width)
                                    .setDuration(100);
                            oa1.start();
                            result = true;
                            isOpen = true;
                        }
                        break;
                }
                return result;
            }
        });
    }
}
