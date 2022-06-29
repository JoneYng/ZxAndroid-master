package com.hzsoft.lib.base.module.provider

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Describe:
 * 日历
 */
interface ICalendarProvider : IProvider {
    val mainCalendarFragment: Fragment
}