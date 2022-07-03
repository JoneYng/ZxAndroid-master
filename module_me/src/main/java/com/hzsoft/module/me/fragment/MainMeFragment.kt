package com.hzsoft.module.me.fragment

import android.view.View
import android.widget.Button
import com.zx.lib.base.view.BaseFragment
import com.hzsoft.module.me.R
import com.hzsoft.module.me.activity.test.TestActivity

/**
 * Describe:
 * 首页
 * @author zhou
 * @Date 2020/12/3
 */
class MainMeFragment : BaseFragment() {

    companion object {
        fun newsInstance(): MainMeFragment {
            return MainMeFragment()
        }
    }


    override fun onBindLayout(): Int = R.layout.fragment_me_main

    override fun initView(mView: View) {

    }

    override fun initData() {

    }

    override fun initListener() {
        findViewById<Button>(R.id.button_1).setOnClickListener(this::onClick)
    }

    override fun enableToolbar(): Boolean = true

    override fun getTootBarTitle(): String = "设置"

    override fun onClick(v: View?) {
        if (beFastClick()) {
            return
        }
        when (v?.id) {
            R.id.button_1 -> {
                TestActivity.start(mContext)
            }
        }
    }
}
