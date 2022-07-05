package com.zx.pay.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.zx.pay.constans.AliConstants;

import java.util.Map;

/**
 * @description:支付宝支付管理类
 * @author: zhouxiang
 * @created: 2022/07/05 13:07
 * @version: V1.0
 */
public class AlipayManager {
    private PayTask mPayTask;


    public interface AlipayResultCallBack {
        void onSuccess(); //支付成功
        void onDealing();    //正在处理中 小概率事件 此时以验证服务端异步通知结果为准
        void onError(int error_code);   //支付失败
        void onCancel();    //支付取消
    }
    private static AlipayManager mAlipayManager;

    public static void init(Context context) {
        if (mAlipayManager == null) {
            mAlipayManager = new AlipayManager(context);
        }
    }
    public static AlipayManager getInstance() {
        return mAlipayManager;
    }
    private AlipayManager(Context context) {
        mPayTask = new PayTask((Activity) context);
    }

    //支付
    public void doPay(String params, AlipayResultCallBack callback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> pay_result = mPayTask.payV2(params,true);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(callback == null) {
                            return;
                        }

                        if(pay_result == null) {
                            callback.onError(AliConstants.ERROR_RESULT);
                            return;
                        }

                        String resultStatus = pay_result.get("resultStatus");
                        if(TextUtils.equals(resultStatus, "9000")) {    //支付成功
                            callback.onSuccess();
                        } else if(TextUtils.equals(resultStatus, "8000")) { //支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            callback.onDealing();
                        } else if(TextUtils.equals(resultStatus, "6001")) {		//支付取消
                            callback.onCancel();
                        } else if(TextUtils.equals(resultStatus, "6002")) {     //网络连接出错
                            callback.onError(AliConstants.ERROR_NETWORK);
                        } else if(TextUtils.equals(resultStatus, "4000")) {        //支付错误
                            callback.onError(AliConstants.ERROR_PAY);
                        }
                    }
                });
            }
        }).start();
    }
}