package com.zx.lib.net.error.mapper

import com.zx.lib.net.error.Error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
