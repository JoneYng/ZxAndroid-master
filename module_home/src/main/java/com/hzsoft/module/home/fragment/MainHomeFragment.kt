package com.hzsoft.module.home.fragment

import android.view.View
import com.hzsoft.module.home.adapter.MainHomeAdapter
import com.zx.lib.base.utils.ThreadUtils
import com.zx.lib.base.view.BaseMvvmRefreshDataBindingFragment
import com.zx.lib.common.utils.EnvironmentUtil
import com.zx.lib.domain.entity.Demo
import com.zx.lib.log.KLog
import com.hzsoft.module.home.BR
import com.hzsoft.module.home.R
import com.hzsoft.module.home.databinding.FragmentHomeMainBinding
import com.hzsoft.module.home.viewmodel.MainHomeViewModel
import com.xiaomi.push.it
import com.zx.lib.net.dto.Resource
import com.zx.lib.net.utils.ext.launch
import com.zx.lib.net.utils.ext.observe

/**
 * Describe:
 * 首页
 *
 * @author zhou
 * @Date 2020/12/3
 */
class MainHomeFragment :
    BaseMvvmRefreshDataBindingFragment<Demo, FragmentHomeMainBinding, MainHomeViewModel>() {

    companion object {
        fun newsInstance(): MainHomeFragment {
            return MainHomeFragment()
        }
    }

    private lateinit var mAdapter: MainHomeAdapter

    override fun onBindVariableId(): Int = BR.viewModel

    override fun onBindViewModel(): Class<MainHomeViewModel> = MainHomeViewModel::class.java

    override fun initViewObservable() {
        observe(mViewModel.recipesLiveData, ::handleRecipesList)
    }

    override fun onBindLayout(): Int = R.layout.fragment_home_main

    override fun initView(mView: View) {
        mAdapter = MainHomeAdapter()
        mAdapter.bindSkeletonScreen(
            mBinding.mRecyclerView,
            R.layout.skeleton_default_service_item,
            8
        )
    }


    override fun initData() {
        onRefreshEvent()
        KLog.d(TAG, EnvironmentUtil.Storage.getCachePath(mContext))
    }

    var firstLoad = true

    override fun onRefreshEvent() {
        // 为了展示骨架屏
        if (firstLoad) {
            firstLoad = false
            ThreadUtils.runOnUiThread({ mViewModel.refreshData() }, 1000)
        } else {
            mViewModel.refreshData()
        }
    }

    override fun onLoadMoreEvent() {
        mViewModel.loadMore()
    }

    override fun onBindRefreshLayout(): Int = R.id.mRefreshLayout

    override fun enableRefresh(): Boolean = true

    override fun enableLoadMore(): Boolean = false

    override fun enableToolbar(): Boolean = true

    override fun getTootBarTitle(): String = "首页"

    private fun handleRecipesList(resource: Resource<List<Demo>>) {
        resource.launch {
            it?.apply {
                bindListData(recipes = ArrayList(this))
            }
        }
    }

    private fun bindListData(recipes: ArrayList<Demo>) {
        mAdapter.setNewInstance(recipes)
    }
}
