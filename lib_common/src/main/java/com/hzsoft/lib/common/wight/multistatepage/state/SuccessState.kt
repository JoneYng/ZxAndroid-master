package com.hzsoft.lib.common.wight.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.hzsoft.lib.common.wight.multistatepage.MultiState
import com.hzsoft.lib.common.wight.multistatepage.MultiStateContainer

/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:11
 */
class SuccessState : MultiState() {
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return View(context)
    }

    override fun onMultiStateViewCreate(view: View) = Unit

}