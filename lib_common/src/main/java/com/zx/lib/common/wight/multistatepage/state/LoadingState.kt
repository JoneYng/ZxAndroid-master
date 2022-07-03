package com.zx.lib.common.wight.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zx.lib.common.R
import com.zx.lib.common.wight.multistatepage.MultiState
import com.zx.lib.common.wight.multistatepage.MultiStateContainer
import com.zx.lib.common.wight.multistatepage.MultiStatePage

/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:15
 */
class LoadingState : MultiState() {
    private lateinit var tvLoadingMsg: TextView
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.mult_state_loading, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvLoadingMsg = view.findViewById(R.id.tv_loading_msg)
        setLoadingMsg(MultiStatePage.config.loadingMsg)
    }

    fun setLoadingMsg(loadingMsg: String) {
        tvLoadingMsg.text = loadingMsg
    }
}