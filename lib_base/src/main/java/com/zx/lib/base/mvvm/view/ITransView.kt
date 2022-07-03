package com.zx.lib.base.mvvm.view

/**
 *
 * @author zhou
 * @time 2020/12/2
 */
interface ITransView {
    //显示背景透明小菊花View,例如删除操作
    fun showTransLoadingView()

    //隐藏背景透明小菊花View
    fun hideTransLoadingView()
}
