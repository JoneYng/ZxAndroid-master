package com.hzsoft.lib.base.view

import android.view.ViewStub
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hzsoft.lib.base.mvvm.viewmodel.BaseViewModel

/**
 * Describe:
 * 基础 DataBinding 页面
 *
 * @author zhou
 * @Date 2020/12/17
 */
abstract class BaseMvvmDataBindingFragment<V : ViewDataBinding, VM : BaseViewModel> :
    BaseMvvmFragment<VM>() {
    protected lateinit var mBinding: V
    private var viewModelId = 0

    override fun initContentView(mViewStubContent: ViewStub) {
        mViewStubContent.layoutResource = onBindLayout()
        initViewDataBinding(mViewStubContent)
        mViewStubContent.inflate()
    }

    private fun initViewDataBinding(mViewStubContent: ViewStub) {
        viewModelId = onBindVariableId()
        mViewStubContent.setOnInflateListener { _, inflated ->
            mBinding = DataBindingUtil.bind<V>(inflated)!!
            mBinding.setVariable(viewModelId, mViewModel)
        }
    }

    abstract fun onBindVariableId(): Int

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}
