package com.zx.lib.common.utils

import android.content.Context
import android.os.Vibrator
import com.zx.lib.base.BaseApplication

/**
 * 手机振动
 *
 * @author zhou
 * @time 2021/11/23
 */
object VibrateTool {
    private var vibrator: Vibrator? = null

    /**
     * 简单震动
     * @param context     调用震动的Context
     * @param millisecond 震动的时间，毫秒
     */
    @JvmStatic
    fun vibrateOnce(millisecond: Int) {
        vibrator =
            BaseApplication.getContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator!!.vibrate(millisecond.toLong())
    }

    /**
     * 复杂的震动
     * @param context 调用震动的Context
     * @param pattern 震动形式
     * 数组参数意义：
     * 第一个参数为等待指定时间后开始震动，
     * 震动时间为第二个参数。
     * 后边的参数依次为等待震动和震动的时间
     * @param repeate 震动的次数，-1不重复，非-1为从pattern的指定下标开始重复 0为一直震动
     */
    @JvmStatic
    fun vibrateComplicated(context: Context, pattern: LongArray?, repeate: Int) {
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator!!.vibrate(pattern, repeate)
    }

    /**
     * 停止震动
     */
    @JvmStatic
    fun vibrateStop() {
        if (vibrator != null) {
            vibrator!!.cancel()
        }
    }
}