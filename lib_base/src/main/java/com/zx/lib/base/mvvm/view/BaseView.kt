package com.zx.lib.base.mvvm.view

/**
 *
 * @author zhou
 * @time 2020/12/2
 */
interface BaseView : ILoadView, INoDataView, ITransView, INetErrView {
    fun initListener()
    fun initData()
    fun finishActivity()
}
