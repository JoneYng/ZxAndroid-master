package com.zx.android

import com.hzsoft.lib.base.manager.ActivityManager
import com.hzsoft.lib.base.module.ModuleApplication

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
    }
}
