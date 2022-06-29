package com.hzsoft.lib.common.utils.ext.view

import android.widget.TextView

/**
 * Describe:
 * <p></p>
 *
 * @author zhou
 * @Date 2020/12/7
 */

/**
 * if [TextView.getText] is not empty, invoke f()
 * otherwise invoke t()
 */
fun TextView.notEmpty(f: TextView.() -> Unit, t: TextView.() -> Unit) {
    if (text.toString().isNotEmpty()) f() else t()
}