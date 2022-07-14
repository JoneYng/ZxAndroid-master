package com.zx.lib.common.utils.ext.view

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.TypedValue
import android.view.View


import com.zx.lib.common.R
import java.io.Closeable

////////////////////////////////////主要存放kotlin中的扩展方法/////////////////////////////////////////
/**
 * dp转px
 */
fun Float.dp2Px(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}

/**
 * sp转px
 */
fun Float.sp2Px(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )
}


/***
 * 设置延迟时间的View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @return T
 */
fun <T : View> T.withTrigger(delay: Long = 600): T {
    triggerDelay = delay
    return this
}

/***
 * 点击事件的View扩展
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {

    if (clickEnable()) {
        block(this)
    }
}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delay Long 延迟时间，默认500毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(delay: Long = 500, block: (T) -> Unit) {
    triggerDelay = delay
    setOnClickListener {
        if (clickEnable()) {
            block(this)
        }
    }
}


/**
 * 给view添加一个上次触发时间的属性，用来屏蔽双击操作
 */
private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(R.id.triggerLastTimeKeyAssembly) != null) getTag(R.id.triggerLastTimeKeyAssembly) as Long else 0
    set(value) {
        setTag(R.id.triggerLastTimeKeyAssembly, value)
    }

/**
 * 给view添加一个延迟的属性，用来屏蔽双击操作
 */
private var <T : View> T.triggerDelay: Long
    get() = if (getTag(R.id.triggerDelayKeyAssembly) != null) getTag(R.id.triggerDelayKeyAssembly) as Long else -1
    set(value) {
        setTag(R.id.triggerDelayKeyAssembly, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

//分割元素数量相同的list
 fun <T> List<T>?.averageAssignFixLength(splitItemNum: Int): List<List<T>> {
    val result = ArrayList<List<T>>()
    if (this != null && this.run { isNotEmpty() } && splitItemNum > 0) {
        if (size <= splitItemNum) {
            // 源List元素数量小于等于目标分组数量
            result.add(this)
        } else {
            // 计算拆分后list数量
            val splitNum =
                if (size % splitItemNum == 0) size / splitItemNum else size / splitItemNum + 1
            var value: List<T>? = null
            for (i in 0 until splitNum) {
                value = if (i < splitNum - 1) {
                    subList(i * splitItemNum, (i + 1) * splitItemNum)
                } else {
                    // 最后一组
                    subList(i * splitItemNum, this.size)
                }
                result.add(value)
            }
        }
    }
    return result
}

/**
 * 格式化0
 */
fun Double.formatZero(): Any {
    return if (this % 1.0 == 0.0) {
        this.toInt()
    } else this
}

