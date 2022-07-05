package com.zx.pay.constans


/**
 * @description:
 * @author: zhouxiang
 * @created: 2022/07/05 10:40
 * @version: V1.0
 */
interface AliConstants {
    companion object {
        const val ERROR_RESULT = 1 //支付结果解析错误
        const val ERROR_PAY = 2 //支付失败
        const val ERROR_NETWORK = 3 //网络连接错误
    }
}