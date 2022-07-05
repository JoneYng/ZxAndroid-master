package com.zx.android.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.zx.pay.R
import com.zx.pay.wechat.WeChatManager

/**
 * 微信支付
 */
class WXPayEntryActivity : Activity(), IWXAPIEventHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wxpay_entry)
        if (WeChatManager.getInstance() != null) {
            WeChatManager.getInstance().wxApi.handleIntent(intent, this)
        } else {
            finish()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (WeChatManager.getInstance() != null) {
            WeChatManager.getInstance().getWXApi().handleIntent(intent, this)
        }
    }

    //当微信发送请求到你的应用，将通过IWXAPIEventHandler接口的onReq方法进行回调
    override fun onReq(baseReq: BaseReq?) {}

    //  0	成功	展示成功页面
    // -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
    //-2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
    //应用请求微信的响应结果将通过onResp回调
    //如果支付成功则调用支付结果查询的接口，设置支付状态
    // 注意一定不能以客户端返回作为用户支付的结果，应以服务器端的接收的支付通知或查询API返回的结果为准
    override fun onResp(resp: BaseResp) {
        Log.i("WXPayEntryActivity", "wechat/////////onPayFinish, errCode = " + resp.errCode)
        if (resp.type == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (WeChatManager.getInstance() != null) {
                WeChatManager.getInstance().onResp(resp.errCode)
            }
            finish()
        }
    }
}