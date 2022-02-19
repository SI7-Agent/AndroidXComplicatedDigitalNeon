package com.si7agent.androidx_digital_neon.data.watchface

import android.content.Context
import android.graphics.drawable.Icon
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.UserStyleSetting.ListUserStyleSetting
import com.si7agent.androidx_digital_neon.R

// Defaults for all styles.
// X_COLOR_STYLE_ID - id in watch face database for each style id.
// X_COLOR_STYLE_NAME_RESOURCE_ID - String name to display in the user settings UI for the style.
// X_COLOR_STYLE_ICON_ID - Icon to display in the user settings UI for the style.
const val AMBIENT_COLOR_STYLE_ID = "ambient_style_id"
private const val AMBIENT_COLOR_STYLE_NAME_RESOURCE_ID = R.string.ambient_style_name
private const val AMBIENT_COLOR_STYLE_ICON_ID = R.drawable.theme1_neon_bg

const val RED_COLOR_STYLE_ID = "red_style_id"
private const val RED_COLOR_STYLE_NAME_RESOURCE_ID = R.string.red_style_name
private const val RED_COLOR_STYLE_ICON_ID = R.drawable.theme2_neon_bg

const val GREEN_COLOR_STYLE_ID = "green_style_id"
private const val GREEN_COLOR_STYLE_NAME_RESOURCE_ID = R.string.green_style_name
private const val GREEN_COLOR_STYLE_ICON_ID = R.drawable.theme4_neon_bg

const val PURPLE_COLOR_STYLE_ID = "purple_style_id"
private const val PURPLE_COLOR_STYLE_NAME_RESOURCE_ID = R.string.purple_style_name
private const val PURPLE_COLOR_STYLE_ICON_ID = R.drawable.theme3_neon_bg

const val WHITE_COLOR_STYLE_ID = "white_style_id"
private const val WHITE_COLOR_STYLE_NAME_RESOURCE_ID = R.string.white_style_name
private const val WHITE_COLOR_STYLE_ICON_ID = R.drawable.theme1_neon_bg


enum class ColorStyleData(
    val id: String,
    @StringRes val nameResourceId: Int,
    @DrawableRes val iconResourceId: Int,
    @DrawableRes val complicationStyleDrawableId: Int,
    @ColorRes val primaryColorId: Int,
    @ColorRes val secondaryColorId: Int,
    @ColorRes val backgroundColorId: Int,
    @ColorRes val outerElementColorId: Int
) {
    AMBIENT(
        id = AMBIENT_COLOR_STYLE_ID,
        nameResourceId = AMBIENT_COLOR_STYLE_NAME_RESOURCE_ID,
        iconResourceId = AMBIENT_COLOR_STYLE_ICON_ID,
        complicationStyleDrawableId = R.drawable.complication_white_style,
        primaryColorId = R.color.ambient_primary_color,
        secondaryColorId = R.color.ambient_secondary_color,
        backgroundColorId = R.color.ambient_background_color,
        outerElementColorId = R.color.ambient_outer_element_color
    ),

    RED(
        id = RED_COLOR_STYLE_ID,
        nameResourceId = RED_COLOR_STYLE_NAME_RESOURCE_ID,
        iconResourceId = RED_COLOR_STYLE_ICON_ID,
        complicationStyleDrawableId = R.drawable.complication_red_style,
        primaryColorId = R.color.red_primary_color,
        secondaryColorId = R.color.red_secondary_color,
        backgroundColorId = R.color.red_background_color,
        outerElementColorId = R.color.red_outer_element_color
    ),

    GREEN(
        id = GREEN_COLOR_STYLE_ID,
        nameResourceId = GREEN_COLOR_STYLE_NAME_RESOURCE_ID,
        iconResourceId = GREEN_COLOR_STYLE_ICON_ID,
        complicationStyleDrawableId = R.drawable.complication_green_style,
        primaryColorId = R.color.green_primary_color,
        secondaryColorId = R.color.green_secondary_color,
        backgroundColorId = R.color.green_background_color,
        outerElementColorId = R.color.green_outer_element_color
    ),

    PURPLE(
        id = PURPLE_COLOR_STYLE_ID,
        nameResourceId = PURPLE_COLOR_STYLE_NAME_RESOURCE_ID,
        iconResourceId = PURPLE_COLOR_STYLE_ICON_ID,
        complicationStyleDrawableId = R.drawable.complication_purple_style,
        primaryColorId = R.color.purple_primary_color,
        secondaryColorId = R.color.purple_secondary_color,
        backgroundColorId = R.color.purple_background_color,
        outerElementColorId = R.color.purple_outer_element_color
    ),

    WHITE(
        id = WHITE_COLOR_STYLE_ID,
        nameResourceId = WHITE_COLOR_STYLE_NAME_RESOURCE_ID,
        iconResourceId = WHITE_COLOR_STYLE_ICON_ID,
        complicationStyleDrawableId = R.drawable.complication_white_style,
        primaryColorId = R.color.white_primary_color,
        secondaryColorId = R.color.white_secondary_color,
        backgroundColorId = R.color.white_background_color,
        outerElementColorId = R.color.white_outer_element_color
    );

    companion object {
        fun getColorStyleConfig(id: String): ColorStyleData {
            return when (id) {
                AMBIENT.id -> AMBIENT
                RED.id -> RED
                GREEN.id -> GREEN
                PURPLE.id -> PURPLE
                WHITE.id -> WHITE
                else -> WHITE
            }
        }

        /**
         * Returns a list of [UserStyleSetting.ListUserStyleSetting.ListOption] for all
         * ColorStyleIdAndResourceIds enums. The watch face settings APIs use this to set up
         * options for the user to select a style.
         */
        fun toOptionList(context: Context): List<ListUserStyleSetting.ListOption> {
            val colorStyleIdAndResourceIdsList = enumValues<ColorStyleData>()

            return colorStyleIdAndResourceIdsList.map { colorStyleIdAndResourceIds ->
                ListUserStyleSetting.ListOption(
                    UserStyleSetting.Option.Id(colorStyleIdAndResourceIds.id),
                    context.resources,
                    colorStyleIdAndResourceIds.nameResourceId,
                    Icon.createWithResource(
                        context,
                        colorStyleIdAndResourceIds.iconResourceId
                    )
                )
            }
        }
    }
}