package com.wj.uidemo.utils

import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.COMPLEX_UNIT_SP
import com.wj.uidemo.MyApp

/**
 * 拓展类
 * Created by baolongxiang
 */

private val metrics = MyApp.getInstance().applicationContext.resources.displayMetrics

val Float.dp: Float
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this, metrics)

val Int.dp: Int
    get() = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()

val Float.sp: Float
    get() = TypedValue.applyDimension(COMPLEX_UNIT_SP, this, metrics)

val Int.sp: Int
    get() = TypedValue.applyDimension(COMPLEX_UNIT_SP, this.toFloat(), metrics).toInt()

val Number.px: Number
    get() = this

val Number.dp2px: Float
    get() = this.toFloat() * metrics.density + 0.5f

val Number.sp2px: Float
    get() = this.toFloat() * metrics.scaledDensity + 0.5f