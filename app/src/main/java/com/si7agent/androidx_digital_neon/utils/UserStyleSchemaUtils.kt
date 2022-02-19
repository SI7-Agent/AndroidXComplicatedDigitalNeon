package com.si7agent.androidx_digital_neon.utils

import android.content.Context
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.si7agent.androidx_digital_neon.R
import com.si7agent.androidx_digital_neon.data.watchface.ColorStyleData
import com.si7agent.androidx_digital_neon.data.watchface.FontStyleData
import com.si7agent.androidx_digital_neon.data.watchface.HOUR_PIPS_DEFAULT

const val COLOR_STYLE_SETTING = "color_style_setting"
const val FONT_STYLE_SETTING = "font_style_setting"
const val HOUR_PIPS_STYLE_SETTING = "hour_pips_style_setting"

fun createUserStyleSchema(context: Context): UserStyleSchema {
    // 1. Allows user to change the color styles of the watch face (if any are available).
    val colorStyleSetting =
        UserStyleSetting.ListUserStyleSetting(
            UserStyleSetting.Id(COLOR_STYLE_SETTING),
            context.resources,
            R.string.colors_style_setting,
            R.string.colors_style_setting_description,
            null,
            ColorStyleData.toOptionList(context),
            listOf(
                WatchFaceLayer.BASE,
                WatchFaceLayer.COMPLICATIONS,
                WatchFaceLayer.COMPLICATIONS_OVERLAY
            )
        )

    // 2. Allows user to toggle on/off the hour pips (dashes around the outer edge of the watch
    // face).
    val drawHourPipsStyleSetting = UserStyleSetting.BooleanUserStyleSetting(
        UserStyleSetting.Id(HOUR_PIPS_STYLE_SETTING),
        context.resources,
        R.string.watchface_pips_setting,
        R.string.watchface_pips_setting_description,
        null,
        listOf(WatchFaceLayer.BASE),
        HOUR_PIPS_DEFAULT
    )

    // 3. Allows user to change the font style.
    val fontStyleSetting = UserStyleSetting.ListUserStyleSetting(
        UserStyleSetting.Id(FONT_STYLE_SETTING),
        context.resources,
        R.string.font_style_setting,
        R.string.font_style_setting_description,
        null,
        FontStyleData.toOptionList(context),
        listOf(
            WatchFaceLayer.BASE,
            WatchFaceLayer.COMPLICATIONS,
            WatchFaceLayer.COMPLICATIONS_OVERLAY
        )
    )

    // 4. Create style settings to hold all options.
    return UserStyleSchema(
        listOf(
            colorStyleSetting,
            drawHourPipsStyleSetting,
            fontStyleSetting
        )
    )
}
