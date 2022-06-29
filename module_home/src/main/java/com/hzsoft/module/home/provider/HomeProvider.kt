package com.hzsoft.module.home.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.hzsoft.lib.base.module.provider.IHomeProvider
import com.hzsoft.lib.base.module.router.ARouterConfig.Companion.FRAGMENT_HOME_MAIN
import com.hzsoft.module.home.fragment.MainHomeFragment

/**
 * Describe:
 * 首页服务
 *
 * @author zhou
 * @Date 2020/12/3
 */
@Route(path = FRAGMENT_HOME_MAIN, name = "首页服务")
class HomeProvider : IHomeProvider {
    override val mainHomeFragment: Fragment
        get() = MainHomeFragment.newsInstance()

    override fun init(context: Context?) {

    }
}