package com.zx.lib.common.wight.multistatepage


import android.app.Activity
import android.view.View

/**
 * @ProjectName: MultiStatePage
 * @Description: TODO
 * @CreateDate: 2020/9/17 17:10
 */
fun View.bindMultiState() = MultiStatePage.bindMultiState(this)

fun Activity.bindMultiState() = MultiStatePage.bindMultiState(this)