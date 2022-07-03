package com.zx.lib.base.mvvm.view

import androidx.annotation.DrawableRes

/**
 *
 * @author zhou
 * @time 2020/12/2
 */
interface INoDataView {
    //显示无数据View
    fun showNoDataView()

    //隐藏无数据View
    fun hideNoDataView()

    //显示指定资源的无数据View
    fun showNoDataView(@DrawableRes resid: Int)
}
