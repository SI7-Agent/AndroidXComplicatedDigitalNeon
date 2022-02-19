package com.si7agent.androidx_digital_neon.data.watchface

import android.content.Context
import android.graphics.drawable.Icon
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.UserStyleSetting.ListUserStyleSetting
import com.si7agent.androidx_digital_neon.R

// Defaults for all styles.
// X_FONT_STYLE_ID - id in watch face database for each style id.
// X_FONT_STYLE_HOUR_SIZE - Size of the font drawing hour's digits.
// X_FONT_STYLE_MINUTE_SIZE - Size of the font drawing minute's digits.
// X_FONT_STYLE_SECOND_SIZE - Size of the font drawing second's digits.
// X_FONT_STYLE_COMPLICATION_SIZE - Size of the font drawing complication's data.
// X_FONT_STYLE_NAME_RESOURCE_ID - String name to display in the user settings UI for the style.
// X_FONT_STYLE_FONT_ID - Font to display.
const val AM3_FONT_STYLE_ID = "3am_style_id"
private const val AM3_FONT_STYLE_HOUR_SIZE = R.dimen.am3_hour_style
private const val AM3_FONT_STYLE_MINUTE_SIZE = R.dimen.am3_minute_style
private const val AM3_FONT_STYLE_SECOND_SIZE = R.dimen.am3_second_style
private const val AM3_FONT_STYLE_COMPLICATION_SIZE = R.dimen.am3_complication_style
private const val AM3_FONT_STYLE_NAME_RESOURCE_ID = R.string.am3_style
private const val AM3_FONT_STYLE_FONT_ID = R.font.am3_style

const val AURACH_FONT_STYLE_ID = "aurach_style_id"
private const val AURACH_FONT_STYLE_HOUR_SIZE = R.dimen.aurach_hour_style
private const val AURACH_FONT_STYLE_MINUTE_SIZE = R.dimen.aurach_minute_style
private const val AURACH_FONT_STYLE_SECOND_SIZE = R.dimen.aurach_second_style
private const val AURACH_FONT_STYLE_COMPLICATION_SIZE = R.dimen.aurach_complication_style
private const val AURACH_FONT_STYLE_NAME_RESOURCE_ID = R.string.aurach_style
private const val AURACH_FONT_STYLE_FONT_ID = R.font.aurach_style

const val BASIS33_FONT_STYLE_ID = "basis33_style_id"
private const val BASIS33_FONT_STYLE_HOUR_SIZE = R.dimen.basis33_hour_style
private const val BASIS33_FONT_STYLE_MINUTE_SIZE = R.dimen.basis33_minute_style
private const val BASIS33_FONT_STYLE_SECOND_SIZE = R.dimen.basis33_second_style
private const val BASIS33_FONT_STYLE_COMPLICATION_SIZE = R.dimen.basis33_complication_style
private const val BASIS33_FONT_STYLE_NAME_RESOURCE_ID = R.string.basis33_style
private const val BASIS33_FONT_STYLE_FONT_ID = R.font.basis33_style

const val MINAEFF_FONT_STYLE_ID = "minaeff_style_id"
private const val MINAEFF_FONT_STYLE_HOUR_SIZE = R.dimen.minaeff_hour_style
private const val MINAEFF_FONT_STYLE_MINUTE_SIZE = R.dimen.minaeff_minute_style
private const val MINAEFF_FONT_STYLE_SECOND_SIZE = R.dimen.minaeff_second_style
private const val MINAEFF_FONT_STYLE_COMPLICATION_SIZE = R.dimen.minaeff_complication_style
private const val MINAEFF_FONT_STYLE_NAME_RESOURCE_ID = R.string.minaeff_style
private const val MINAEFF_FONT_STYLE_FONT_ID = R.font.minaeff_style


enum class FontStyleData(
    val id: String,
    @DimenRes val hourDimension: Int,
    @DimenRes val minuteDimension: Int,
    @DimenRes val secondDimension: Int,
    @DimenRes val complicationDimension: Int,
    @StringRes val nameResourceId: Int,
    @FontRes val fontResourceId: Int,
) {
    AM3(
        id = AM3_FONT_STYLE_ID,
        hourDimension = AM3_FONT_STYLE_HOUR_SIZE,
        minuteDimension = AM3_FONT_STYLE_MINUTE_SIZE,
        secondDimension = AM3_FONT_STYLE_SECOND_SIZE,
        complicationDimension = AM3_FONT_STYLE_COMPLICATION_SIZE,
        nameResourceId = AM3_FONT_STYLE_NAME_RESOURCE_ID,
        fontResourceId = AM3_FONT_STYLE_FONT_ID,
    ),

    AURACH(
        id = AURACH_FONT_STYLE_ID,
        hourDimension = AURACH_FONT_STYLE_HOUR_SIZE,
        minuteDimension = AURACH_FONT_STYLE_MINUTE_SIZE,
        secondDimension = AURACH_FONT_STYLE_SECOND_SIZE,
        complicationDimension = AURACH_FONT_STYLE_COMPLICATION_SIZE,
        nameResourceId = AURACH_FONT_STYLE_NAME_RESOURCE_ID,
        fontResourceId = AURACH_FONT_STYLE_FONT_ID,
    ),

    BASIS33(
        id = BASIS33_FONT_STYLE_ID,
        hourDimension = BASIS33_FONT_STYLE_HOUR_SIZE,
        minuteDimension = BASIS33_FONT_STYLE_MINUTE_SIZE,
        secondDimension = BASIS33_FONT_STYLE_SECOND_SIZE,
        complicationDimension = BASIS33_FONT_STYLE_COMPLICATION_SIZE,
        nameResourceId = BASIS33_FONT_STYLE_NAME_RESOURCE_ID,
        fontResourceId = BASIS33_FONT_STYLE_FONT_ID,
    ),

    MINAEFF(
        id = MINAEFF_FONT_STYLE_ID,
        hourDimension = MINAEFF_FONT_STYLE_HOUR_SIZE,
        minuteDimension = MINAEFF_FONT_STYLE_MINUTE_SIZE,
        secondDimension = MINAEFF_FONT_STYLE_SECOND_SIZE,
        complicationDimension = MINAEFF_FONT_STYLE_COMPLICATION_SIZE,
        nameResourceId = MINAEFF_FONT_STYLE_NAME_RESOURCE_ID,
        fontResourceId = MINAEFF_FONT_STYLE_FONT_ID,
    );

    companion object {
        fun getFontStyleConfig(id: String): FontStyleData {
            return when (id) {
                AM3.id -> AM3
                AURACH.id -> AURACH
                BASIS33.id -> BASIS33
                MINAEFF.id -> MINAEFF
                else -> AM3
            }
        }

        fun toOptionList(context: Context): List<ListUserStyleSetting.ListOption> {
            val fontStyleDataList = enumValues<ColorStyleData>()

            return fontStyleDataList.map { fontStyleData ->
                ListUserStyleSetting.ListOption(
                    UserStyleSetting.Option.Id(fontStyleData.id),
                    context.resources,
                    fontStyleData.nameResourceId,
                    Icon.createWithResource(
                        context,
                        R.drawable.theme1_neon_bg
                    )
                )
            }
        }
    }
}