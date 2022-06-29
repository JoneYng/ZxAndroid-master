package com.hzsoft.lib.base.module.router

/**
 * Describe：路由页面常量配置 注意：路径至少需要两级 {/xx/xx}
 *
 */
interface ARouterConfig {
    companion object {
        /**
         * 首页服务
         */
        const val FRAGMENT_HOME_MAIN = "/home/main"

        /**
         * 日历服务
         */
        const val FRAGMENT_CALENDAR_MAIN = "/calendar/main"

        /**
         * 个人中心服务
         */
        const val FRAGMENT_ME_MAIN = "/me/main"


    }
}