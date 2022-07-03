package com.zx.android

import com.zx.lib.base.manager.ActivityManager
import com.zx.lib.base.module.ModuleApplication
import com.zx.lib.share.UmengUtils

/**
 * Describe:
 * App
 *
 * @author zhou
 * @Date 2020/12/1
 */
class MyApp : ModuleApplication(){
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityManager.instance)

        UmengUtils.init(this)
    }
}
