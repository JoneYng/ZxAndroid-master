package com.hzsoft.lib.base.event.common

import com.hzsoft.lib.base.event.BaseEvent

/**
 * 基于事件
 *
 * @author zhou
 * @Data
 */
open class BaseActivityEvent<T>(code: Int) : BaseEvent<T>(code)
