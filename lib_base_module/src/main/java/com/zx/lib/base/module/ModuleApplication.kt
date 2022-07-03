package com.zx.lib.base.module

import com.zx.lib.base.BaseApplication
import com.zx.lib.common.URL_BASE
import com.zx.lib.net.config.NetConfig

/**
 * 初始化应用程序
 * @author zhou
 * @time 2020/11/30 23:04
 */
open class ModuleApplication : BaseApplication() {

    override fun initOnlyMainProcessInLowPriorityThread() {
        super.initOnlyMainProcessInLowPriorityThread()
        initNet()
    }

    private fun initNet() {
        val config = NetConfig.Builder()
            .setBaseUrl(URL_BASE)
            .build()
        config.initContext(this)
    }
}
