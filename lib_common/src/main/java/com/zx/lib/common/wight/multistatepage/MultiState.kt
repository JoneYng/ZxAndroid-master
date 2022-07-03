package com.zx.lib.common.wight.multistatepage

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/9/17 12:01
 */
abstract class MultiState {

    /**
     * 创建stateView
     */
    abstract fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View

    /**
     * stateView创建完成
     */
    abstract fun onMultiStateViewCreate(view: View)
}