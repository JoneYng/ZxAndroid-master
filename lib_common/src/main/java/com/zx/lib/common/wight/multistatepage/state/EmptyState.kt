package com.zx.lib.common.wight.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.zx.lib.common.R
import com.zx.lib.common.wight.multistatepage.MultiState
import com.zx.lib.common.wight.multistatepage.MultiStateContainer
import com.zx.lib.common.wight.multistatepage.MultiStatePage

/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:15
 */
class EmptyState : MultiState() {

    private lateinit var tvEmptyMsg: TextView
    private lateinit var imgEmpty: ImageView

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.mult_state_empty, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvEmptyMsg = view.findViewById(R.id.tv_empty_msg)
        imgEmpty = view.findViewById(R.id.img_empty)

        setEmptyMsg(MultiStatePage.config.emptyMsg)
        setEmptyIcon(MultiStatePage.config.emptyIcon)
    }

    fun setEmptyMsg(emptyMsg: String) {
        tvEmptyMsg.text = emptyMsg
    }

    fun setEmptyIcon(@DrawableRes emptyIcon: Int) {
        imgEmpty.setImageResource(emptyIcon)
    }
}