package com.zx.lib.net.error

/**
 * Describe:
 * <p>接口异常</p>
 *
 * @author zhou
 * @Date 2020/12/1
 */
class ApiException constructor(
    var code: Int,
    override var message: String = "",
    var e: Throwable? = null
) : Exception(message, e)