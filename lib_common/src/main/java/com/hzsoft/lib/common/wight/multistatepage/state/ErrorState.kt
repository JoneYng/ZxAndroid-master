package com.hzsoft.lib.common.wight.multistatepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.hzsoft.lib.common.R
import com.hzsoft.lib.common.wight.multistatepage.MultiState
import com.hzsoft.lib.common.wight.multistatepage.MultiStateContainer
import com.hzsoft.lib.common.wight.multistatepage.MultiStatePage

/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:15
 */
class ErrorState : MultiState() {

    private lateinit var tvErrorMsg: TextView
    private lateinit var imgError: ImageView
    private lateinit var tvRetry: TextView

    private var retry: OnRetryClickListener? = null

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.mult_state_error, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvErrorMsg = view.findViewById(R.id.tv_error_msg)
        imgError = view.findViewById(R.id.img_error)
        tvRetry = view.findViewById(R.id.tv_retry)

        setErrorMsg(MultiStatePage.config.errorMsg)
        setErrorIcon(MultiStatePage.config.errorIcon)

        tvRetry.setOnClickListener { retry?.retry() }
    }

    fun setErrorMsg(errorMsg: String) {
        tvErrorMsg.text = errorMsg
    }

    fun setErrorIcon(@DrawableRes errorIcon: Int) {
        imgError.setImageResource(errorIcon)
    }

    fun retry(retry: OnRetryClickListener) {
        this.retry = retry
    }

    fun interface OnRetryClickListener {
        fun retry()
    }
}