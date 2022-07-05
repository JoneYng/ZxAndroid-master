package com.zx.pay.wechat;

/**
 * @description:微信支付管理类
 * @author: zhouxiang
 * @created: 2022/07/05 13:07
 * @version: V1.0
 */
import android.content.Context;
import android.text.TextUtils;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zx.pay.constans.WeChatConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description:微信支付管理
 */
public class WeChatManager {

    private static WeChatManager mWXManager;
    private IWXAPI mWXApi;
    private String mPayParam;
    private WXPayResultCallBack mCallback;

    public static void init(Context context) {
        if (mWXManager == null) {
            mWXManager = new WeChatManager(context);
        }
    }

    private WeChatManager(Context context) {
        //通过WXAPIFactory工厂，获取IWXAPI的实例
        // IWXAPI 是第三方app和微信通信的openapi接口
        mWXApi = WXAPIFactory.createWXAPI(context, WeChatConstants.APP_ID, true);
        //将应用的appId注册到微信
        mWXApi.registerApp(WeChatConstants.APP_ID);
    }

    public static WeChatManager getInstance() {
        return mWXManager;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    public interface WXPayResultCallBack {
        void onSuccess(); //支付成功

        void onError(int error_code);   //支付失败

        void onCancel();    //支付取消
    }

    /**
     * 发起支付
     *
     * @param pay_param 由服务器返回
     * @param callback
     */
    public void doPay(String pay_param, WXPayResultCallBack callback) {
        mPayParam = pay_param;
        mCallback = callback;

        if (!check()) {
            if (mCallback != null) {
                mCallback.onError(WeChatConstants.NO_OR_LOW_WX);
            }
            return;
        }
        JSONObject param = null;
        try {
            param = new JSONObject(mPayParam);
        } catch (JSONException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onError(WeChatConstants.ERROR_PAY_PARAM);
            }
            return;
        }
        if (TextUtils.isEmpty(param.optString("appid"))
                || TextUtils.isEmpty(param.optString("partnerid"))
                || TextUtils.isEmpty(param.optString("prepayid"))
                || TextUtils.isEmpty(param.optString("package"))
                || TextUtils.isEmpty(param.optString("noncestr"))
                || TextUtils.isEmpty(param.optString("timestamp"))
                || TextUtils.isEmpty(param.optString("sign"))) {
            if (mCallback != null) {
                mCallback.onError(WeChatConstants.ERROR_PAY_PARAM);
            }
            return;
        }
        PayReq req = new PayReq();
        //应用ID 微信开放平台审核通过的应用APPID
        req.appId = param.optString("appid");
        //商户号 微信支付分配的商户号
        req.partnerId = param.optString("partnerid");
        //预支付交易会话ID 微信返回的支付交易会话ID
        req.prepayId = param.optString("prepayid");
        //扩展字段 暂填写固定值"Sign=WXPay"
        req.packageValue = param.optString("package");
        //随机字符串 随机字符串，不长于32位
        req.nonceStr = param.optString("noncestr");
        //时间戳
        req.timeStamp = param.optString("timestamp");
        //签名
        req.sign = param.optString("sign");

        mWXApi.sendReq(req);
    }

    /**
     * 支付回调响应
     *
     * @param error_code
     */
    public void onResp(int error_code) {
        if (mCallback == null) {
            return;
        }
        if (error_code == WeChatConstants.PAY_SUCCESS) {   //支付成功
            mCallback.onSuccess();
        } else if (error_code == WeChatConstants.PAY_ERROR) {   //支付错误
            mCallback.onError(WeChatConstants.ERROR_PAY);
        } else if (error_code == WeChatConstants.PAY_CANCEL) {   //支付取消
            mCallback.onCancel();
        }

        mCallback = null;
    }

    /**
     * 检测是否支持微信支付
     *
     * @return
     */
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}