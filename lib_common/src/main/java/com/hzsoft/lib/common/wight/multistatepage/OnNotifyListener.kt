package com.hzsoft.lib.common.wight.multistatepage


import com.hzsoft.lib.common.wight.multistatepage.MultiState


/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/11/4 16:04
 */
fun interface OnNotifyListener<T : MultiState> {
    fun onNotify(multiState: T)
}