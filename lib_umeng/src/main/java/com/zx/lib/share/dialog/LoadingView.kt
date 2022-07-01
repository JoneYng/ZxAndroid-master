package com.zx.lib.share.dialog

import android.app.Dialog
import android.content.Context
import com.zx.lib.share.R
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.StringRes

/**
 * 加载框
 */
class LoadingView(context: Context?) : Dialog(context!!, R.style.loading_wait_dialog) {
    private var progressBar: ProgressBar? = null
    private var tv: TextView? = null
    private var iv: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_view)
        progressBar = findViewById(R.id.progressBar)
        tv = findViewById(R.id.tv)
        iv = findViewById(R.id.iv)
    }

    /**
     * loading
     */
    fun showLoading() {
        iv!!.visibility = View.GONE
        progressBar!!.visibility = View.VISIBLE
    }

    /**
     * 成功
     */
    fun showSuccess() {
        iv!!.setImageResource(R.mipmap.icon_load_success)
        iv!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.GONE
    }

    /**
     * 失败
     */
    fun showFail() {
        iv!!.setImageResource(R.mipmap.icon_load_fail)
        iv!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.GONE
    }

    /**
     * 提示文字
     *
     * @param txt string
     */
    fun setText(txt: String?) {
        tv!!.text = txt
    }

    /**
     * 提示文字
     */
    fun setText(@StringRes txtId: Int) {
        tv!!.setText(txtId)
    }
}