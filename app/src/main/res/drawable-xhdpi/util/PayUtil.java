package com.messcat.shisanhang.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.messcat.shisanhang.ali_pay.AuthResult;
import com.messcat.shisanhang.ali_pay.PayResult;
import com.messcat.shisanhang.model.bean.M_WeChatPayResult;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by Mander on 2016/10/26.
 */

public class PayUtil {

    private static final com.messcat.shisanhang.util.PayUtil instance = new com.messcat.shisanhang.util.PayUtil();

    private PayUtil() {
        super();
    }

    public static com.messcat.shisanhang.util.PayUtil getInstance() {
        return instance;
    }

    // 默认
    public static final int PLACE_DEFAULT = 0x200;
    // 充值
    public static final int PLACE_RECHARGE = 0x201;
    // 购买商品
    public static final int PLACE_CART = 0x202;

    // 标记在哪个地方使用支付
    public int place = PLACE_DEFAULT;

    // 支付宝的Handler
    public static Handler mHandler;

    // 微信支付
    private IWXAPI api;

    /**
     * 确认微信支付
     */
    public void CheckWXPay(Activity activity, M_WeChatPayResult result, int place) {
        this.place = place;
        // 判断当前微信版本是否支付微信支付
        api = WXAPIFactory.createWXAPI(activity, result.getAppId());
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
//            MyApplication.getInstance().weChatPayFlag = MyApplication.SHOP;
            weChatPay(result);
        } else {
            ToastUtil.showShort("当前版本不支持微信支付，请升级微信后重试");
        }
    }

    /**
     * 微信支付
     */
    private void weChatPay(M_WeChatPayResult result) {
        PayReq req = new PayReq();
        req.appId = result.getAppId();
        req.prepayId = result.getPrepayid();
        req.nonceStr = result.getNonceStr();
        req.timeStamp = result.getTimeStamp();
        req.packageValue = result.getPackages();
        req.sign = result.getPaySign();
        req.partnerId = result.getPartnerid();

        boolean flag = api.sendReq(req);
    }

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_AUTH_FLAG = 2;

    class AliPayHandler extends Handler {

        private Activity activity;

        public AliPayHandler(Activity activity) {
            this.activity = activity;
        }

        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        ((OrderDetailActivity) getActivity()).setNeedRefresh(status);
//                        Toast.makeText(OrderDetailFragment.this.getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(OrderDetailFragment.this.getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.showShort("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.showShort("授权失败" + String.format("authCode:%s"));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    /**
     * 支付宝支付业务
     *
     * @param
     */
//    public void payV2(final Activity activity, M_AliPayOrderResult result, int place) {
//
//        this.place = place;
//
//        mHandler = new AliPayHandler(activity);
//
//        String appId = "2016102802384194";
//
////        String rsaPrivateKey1 = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMj+6lU83kG0U7mmEyND0oOPd2MgbWZJbSEUbvJL9r3NPlnxkW/Uuheso7raJzsPcJwFdj1hMOKfLJkgzQNpbNI/lZ75qlgJ0Mq6h95CnC5m2IFroSdw1cICFfn3SQZhav6apUZzR60DkasabVBVXWOCNG6bXKEyOZ5vteUi9/IzAgMBAAECgYEAkblX9iKiVWrDRRcmSuwSRXYPrdvF1WIo79Hbumne469ZpGZi9nx5y05Mit09kjFYiJXhKxE7GyMid4bzfKTD0sW8fEahHCqwQhNWNV/C2gsnmdOpV0vqyVGkKhmWAZlv6i7E5ILEMKcPgo9nzYuqG32vz0/hR89YUp+smHKO+eECQQDy2qyAU7Lxuf2r+v/H7Vh8ONZ3gliwhJPMm8CylxDlRha8MkkPKF254+yVNGrtVIQRbIymctAys1d7xgS72dbDAkEA0+AxWUHw14UL/7zdaaEis/d3juedkfy51Kl1rspkN6mtvJCopK4hxUM1XUUcR0B+znu5+yjztUvlOr9Q4Iof0QJBAO7M3hD1vKD7TZieIIO8PGUyEpnDf2hbhWYNxacW6tM4X0TBgZu5CqiumcWBnJ8wmwjzHz7Q6S7hsOWRqrf5FbcCQQDCZGLCkvaGtoIwzDaU8ItndPhTu/vc8nQJQsU/bmAOlfZaGJsyJLXFSAmRHpR4ZxsxFWGop4UaZJzXjqqEqRXRAkEAxjGQbxO4zj1o+4g3vJuKkhkBkvVKLOF0Mv3RdAyl1lBNB0TX34Ospd5PqWa2Dw/dBd6vO+1dXWBPYrp9G8vrcw==";
////        String appId1 = "2016070601586081";
//
//        /**
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         *
//         * orderInfo的获取必须来自服务端；
//         */
//        String bizContent = "biz_content={\\\"timeout_express\\\":\\\"30m\\\",\\\"seller_id\\\":\\\"2088421964684352\\\",\\\"product_code\\\":\\\"QUICK_MSECURITY_PAY\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"subject\\\":\\\"十三行平台支付宝充值\\\",\\\"body\\\":\\\"十三行平台支付宝充值\\\",\\\"out_trade_no\\\":\\\"20161111120612116\\\"}";
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(appId, bizContent, "" + System.currentTimeMillis());
//    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//    String sign = result.getSign();
//    final String orderInfo = orderParam + "&" + sign;
    //        LogUtil.e(LogUtil.PAY, "orderInfo--------------" + orderInfo);
//        final String orderInfo = "_input_charset=\"utf-8\"&body=\"订单编号2016111112134600\"&it_b_pay=\"30m\"&notify_url=\"http://ylt.messcat.com/alipay.notify.do\"&out_trade_no=\"2016111112134600\"&partner=\"2088911183985360\"&payment_type=\"1\"&seller_id=\"yangletang69@163.com\"&service=\"mobile.securitypay.pay\"&subject=\"下单成功\"&total_fee=\"0.01\"&sign=\"d5%2BZ71PURNJ0YQ%2BD0vf07YbogJp5pl4mZd1JZJV72IRElgQr7w4npbM%2Fve%2BJKCiIlkm%2Fz9CjvXxjWVTOvL8skHOxyTlKBogwx2TPMQSDlRdBxQKXMIZrZ54q%2F2OmT7iC2MtSuS%2F8tih0COCZogt9gpRg43BaB14MhSwLk8DzQm0%3D\"&sign_type=\"RSA\"";


//
//        Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                PayTask aliPay = new PayTask(activity);
//                Map<String, String> result = aliPay.payV2(orderInfo, true);
//                Log.i("msp", result.toString());
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }

    /**
     * 支付宝支付
     */
    public void payV2(final Activity activity, final String resultInfo) {
        if (null == mHandler) mHandler = new AliPayHandler(activity);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask aliPay = new PayTask(activity);
                Map<String, String> result = aliPay.payV2(resultInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
