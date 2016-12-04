package com.messcat.shisanhang.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.messcat.shisanhang.app.APPApplication;
import com.messcat.shisanhang.model.bean.UserInfo;
import com.messcat.shisanhang.model.http.BaseBean;
import com.messcat.shisanhang.model.http.RetrofitHelper;
import com.messcat.shisanhang.widget.LoadingDialog;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mander on 2016/10/30.
 */

public class RequestUtil {

    public static final int REFRESH = 0xA01;

    public static final int UPLOAD = 0xA02;

    private static final com.messcat.shisanhang.util.RequestUtil instance = new com.messcat.shisanhang.util.RequestUtil();

    private RequestUtil() {
        super();
    }

    public static com.messcat.shisanhang.util.RequestUtil getInstance() {
        return instance;
    }

    protected CompositeSubscription mCompositeSubscription;

    public interface CallBack {

        void onAutoLoginCompleted(boolean success, String msg);

    }

    /**
     * 免登录
     */
    public void autoLogin(Activity activity, final CallBack callBack) {

        String mobile = com.messcat.shisanhang.util.SharedPreferenceUtil.getLoginUsername();

        String password = com.messcat.shisanhang.util.SharedPreferenceUtil.getLoginPassword();

        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password))
            return;

        if (null != APPApplication.getInstance().getUser())
            return;

        //Retrofit2单例
        Subscription rxSubscription = RetrofitHelper.getInstance(activity).loginByMobile(Long.parseLong(mobile), password)
                .compose(RxUtil.<BaseBean<UserInfo>>rxSchedulerHelper())
                .subscribe(new Action1<BaseBean<UserInfo>>() {
                    @Override
                    public void call(BaseBean<UserInfo> bean) {
                        //200返回数据
                        if (200 == bean.getStatus()) {
                            APPApplication.getInstance().setUser(bean.getResult());
                            if (null != callBack) callBack.onAutoLoginCompleted(true, "自动登录成功");
                        } else {
                            if (null != callBack)
                                callBack.onAutoLoginCompleted(false, bean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    //获取数据失败
                    @Override
                    public void call(Throwable throwable) {
                        if (null != callBack)
                            callBack.onAutoLoginCompleted(false, "请检查你的网络");
                    }
                });
        //添加网络连接
        addSubscribe(rxSubscription);
    }


    public interface OnPayCurrencyCompletedListener {

        void onPayCurrencyCompleted(BaseBean bean);

    }

    /**
     * 付款
     */
    public void payCurrency(Activity activity, int orderId, int[] passwords, final OnPayCurrencyCompletedListener l) {

        final LoadingDialog dialog = new LoadingDialog(activity);

        String password = "";

        for (int pass : passwords) {
            password += pass;
        }

        LogUtil.e(LogUtil.PAY, "password.............................................." + password);

        dialog.show();
        // Retrofit2单例
        Subscription rxSubscription = RetrofitHelper.getInstance(activity).payCurrency(orderId, password)
                .compose(RxUtil.<BaseBean>rxSchedulerHelper())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {
                        dialog.dissmiss();
                        if (null != l)
                            l.onPayCurrencyCompleted(bean);
                    }
                }, new Action1<Throwable>() {
                    //获取数据失败
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dissmiss();
                        BaseBean bean = new BaseBean();
                        bean.setMessage("请检查你的网络");
                        bean.setStatus(0);
                        l.onPayCurrencyCompleted(bean);
                    }
                });
        //添加网络连接
        addSubscribe(rxSubscription);
    }

    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public interface OnUploadImageCompletedListener {

        void onUploadImageCompleted(String imageName);

    }

    public void imageUploadByBase64(Context context, String base64, final LoadingDialog dialog, final OnUploadImageCompletedListener l) {
        dialog.show();
        // Retrofit2单例
        Subscription rxSubscription = RetrofitHelper.getInstance(context).imageUploadByBase64("a.png", base64)
                .compose(RxUtil.<BaseBean<String>>rxSchedulerHelper())
                .subscribe(new Action1<BaseBean<String>>() {
                    @Override
                    public void call(BaseBean<String> bean) {
                        dialog.dissmiss();
                        if (null != l)
                            l.onUploadImageCompleted(bean.getResult());
                    }
                }, new Action1<Throwable>() {
                    //获取数据失败
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dissmiss();
                        ToastUtil.showShort("请检查你的网络");
//                        BaseBean bean = new BaseBean();
//                        bean.setMessage("请检查你的网络");
//                        bean.setStatus(0);
//                        l.onUploadImageCompleted(null);
                    }
                });
        //添加网络连接
        addSubscribe(rxSubscription);
    }

    // 查询个人中心信息
    public void getMemberCenterInfo(Context context, final OnGetMemberCenterInfoCompletedListener l) {
        // Retrofit2单例
        Subscription rxSubscription = RetrofitHelper.getInstance(context).getMemberCenterInfo()
                .compose(RxUtil.<BaseBean<UserInfo>>rxSchedulerHelper())
                .subscribe(new Action1<BaseBean<UserInfo>>() {
                    @Override
                    public void call(BaseBean<UserInfo> bean) {
                        if (200 == bean.getStatus()) {
                            if (null != l) l.onGetMemberCenterInfoCompleted(bean.getResult());
                        } else {
                            ToastUtil.showShort(bean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    //获取数据失败
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.showShort("请检查你的网络");
                    }
                });
        //添加网络连接
        addSubscribe(rxSubscription);
    }

    public interface OnGetMemberCenterInfoCompletedListener {

        void onGetMemberCenterInfoCompleted(UserInfo userInfo);

    }

}
