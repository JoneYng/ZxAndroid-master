package com.hzsoft.module.me.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zx.lib.base.module.provider.IMeProvider
import com.zx.lib.base.module.router.ARouterConfig.Companion.FRAGMENT_ME_MAIN
import com.hzsoft.module.me.fragment.MainMeFragment

/**
 * Describe:
 * 个人中心服务
 *
 * @author zhou
 * @Date 2020/12/3
 */
@Route(path = FRAGMENT_ME_MAIN, name = "个人中心服务")
class MeProvider : IMeProvider {
    override val mainMeFragment: Fragment
        get() = MainMeFragment.newsInstance()

    override fun init(context: Context?) {

    }

}