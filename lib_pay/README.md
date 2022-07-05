# lib_pay 微信支付宝支付
``` 
微信开放平台有个不成文的规定（文档里没有说明）：
1、支付回调的Activity必须是：你的包名（微信demo里是：net.sourceforge.simcpux）+.wxapi.WXPayEntryActivity.java
2、其他的接口（比如登录、分享）回调的Activity必须是：你的包名（微信demo里是：net.sourceforge.simcpux）+.wxapi.WXEntryActivity.java
3、两个回调的Activity必须要实现IWXAPIEventHandler的接口
``` 
``` 
/**
 * 微信支付
 * @param payParam 支付服务生成的支付参数 json
 */
private fun doWXPay(payParam: String) {
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
 * @param payParam 支付服务生成的支付参数 拼接字符串
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

```
