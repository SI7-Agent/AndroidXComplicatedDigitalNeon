package com.si7agent.androidx_digital_neon.data.watchface

const val HOUR_PIPS_DEFAULT: Boolean = true

/**
 * Represents all data needed to render digital neon watch face.
 */
data class WatchFaceData(
    val activeColorStyle: ColorStyleData = ColorStyleData.WHITE,
    val activeFontStyle: FontStyleData = FontStyleData.AM3,
    val ambientColorStyle: ColorStyleData = ColorStyleData.AMBIENT,
    val drawHourPips: Boolean = HOUR_PIPS_DEFAULT,
)