package com.zx.lib.common.wight.multistatepage




/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/11/4 16:04
 */
fun interface OnNotifyListener<T : MultiState> {
    fun onNotify(multiState: T)
}