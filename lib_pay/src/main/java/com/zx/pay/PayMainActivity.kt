package com.zx.pay

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.zx.pay.alipay.AlipayManager
import com.zx.pay.constans.WeChatConstants

import com.zx.pay.wechat.WeChatManager

/**
 * 支付测试
 */
class PayMainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay_main)
        findViewById<View>(R.id.btn_ali_pay).setOnClickListener { testAliPay("") }
        findViewById<View>(R.id.btn_wechat_pay).setOnClickListener { doWXPay("") }
    }

    /**
     * 微信支付
     * @param payParam 支付服务生成的支付参数 json
     */
    private fun doWXPay(payParam: String) {
//        val wechatJson = JSONObject()
//        wechatJson.put("appid","" )
//        wechatJson.put("partnerid", "")
//        wechatJson.put("prepayid", "")
//        wechatJson.put("package", "")
//        wechatJson.put("noncestr", "")
//        wechatJson.put("timestamp", "")
//        wechatJson.put("sign", "")
        WeChatManager.init(applicationContext) //要在支付前调用
        WeChatManager.getInstance()
            .doPay(payParam, object : WeChatManager.WXPayResultCallBack {
                override fun onSuccess() {
                    Toast.makeText(this@PayMainActivity,"支付成功",Toast.LENGTH_SHORT).show()
                }

                override fun onError(error_code: Int) {
                    when (error_code) {
                        WeChatConstants.NO_OR_LOW_WX -> {
                            Toast.makeText(this@PayMainActivity,"未安装微信或微信版本过低",Toast.LENGTH_SHORT).show()
                        }

                        WeChatConstants.ERROR_PAY_PARAM ->{
                            Toast.makeText(this@PayMainActivity,"参数错误",Toast.LENGTH_SHORT).show()
                        }
                        WeChatConstants.ERROR_PAY -> {
                            Toast.makeText(this@PayMainActivity,"支付失败",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancel() {
                    Toast.makeText(this@PayMainActivity,"支付取消",Toast.LENGTH_SHORT).show()
                }
            })
    }


    /**
     * 支付宝支付测试
     * @param payParam 支付服务生成的支付参数
     */
    fun testAliPay(payParam: String) {
        AlipayManager.init(this);
        AlipayManager.getInstance() .doPay(
            payParam,
        object : AlipayManager.AlipayResultCallBack {
            override fun onSuccess() {
                Toast.makeText(this@PayMainActivity,"支付成功",Toast.LENGTH_SHORT).show()
            }
            override fun onDealing() {
            }
            override fun onError(error_code: Int) {
                Toast.makeText(this@PayMainActivity,"支付错误$error_code",Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(this@PayMainActivity,"支付取消",Toast.LENGTH_SHORT).show()
            }
        })
    }


}