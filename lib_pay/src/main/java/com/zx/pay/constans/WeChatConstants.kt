package com.zx.pay.constans


/**
 * @description:
 * @author: zhouxiang
 * @created: 2022/07/05 10:40
 * @version: V1.0
 */
interface WeChatConstants {
    companion object {
        //微信APPID
        const val APP_ID = ""
        //支付状态
        const val PAY_ERROR = -1 //支付失败
        const val PAY_CANCEL = -2 //支付取消
        const val PAY_SUCCESS = 0 //支付成功

        const val NO_OR_LOW_WX = 1 //未安装微信或微信版本过低
        const val ERROR_PAY_PARAM = 2 //支付参数错误
        const val ERROR_PAY = 3 //支付失败


    }
}