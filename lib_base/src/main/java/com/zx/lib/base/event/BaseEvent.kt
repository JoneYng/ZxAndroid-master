package com.zx.lib.base.event

/**
 * BaseEvent
 *
 * @author zhou
 * @Data 
 */
open class BaseEvent<T> {
    var code: Int = 0
    var data: T? = null

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }
}
