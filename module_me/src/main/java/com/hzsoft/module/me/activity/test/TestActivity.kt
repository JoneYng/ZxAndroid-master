package com.hzsoft.module.me.activity.test

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.hzsoft.lib.base.utils.ToastUtil
import com.hzsoft.lib.base.view.BaseActivity
import com.hzsoft.module.me.R

class TestActivity : BaseActivity(),View.OnClickListener{
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TestActivity::class.java).apply {
            })
        }
    }
    private lateinit var editText: EditText

    override fun getTootBarTitle(): String = "测试一下"

    //开启返回按钮
    override fun enableToolBarLeft(): Boolean = true
    override fun onBindLayout(): Int {
        return  R.layout.activity_test;
    }

    override fun initView() {
        editText = findViewById(R.id.editText)
    }

    override fun initData() {
    }

   override fun initListener() {
        findViewById<Button>(R.id.button_3).setOnClickListener(this::onClick)
        findViewById<Button>(R.id.button_4).setOnClickListener(this::onClick)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_3 -> {
                val trim = editText.text.toString().trim()
                if (trim.isBlank()) {
                    ToastUtil.showToastCenter("输入内容不能为空")
                    return
                }
                SaveStateTestActivity.start(this, trim)
            }
            R.id.button_4 -> {
                RoomTestActivity.start(this)
            }
        }
    }
}