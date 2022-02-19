package com.si7agent.androidx_digital_neon.data.watchface

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.wear.watchface.complications.rendering.ComplicationDrawable

/**
 * Color resources and drawable id needed to render the watch face. Translated from
 * [ColorStyleData] constant ids to actual resources with context at run time.
 *
 * This is only needed when the watch face is active.
 *
 * Note: We do not use the context to generate a [ComplicationDrawable] from the
 * complicationStyleDrawableId (representing the style), because a new, separate
 * [ComplicationDrawable] is needed for each complication. Because the renderer will loop through
 * all the complications and there can be more than one, this also allows the renderer to create
 * as many [ComplicationDrawable]s as needed.
 */
data class WatchFaceColorPalette(
    val activePrimaryColor: Int,
    val activeSecondaryColor: Int,
    val activeBackgroundColor: Int,
    val activeOuterElementColor: Int,
    @DrawableRes val hourPipsStyleDrawableId: Int,
    @DrawableRes val secondStyleDrawableId: Int,
    @DrawableRes val appLauncherStyleDrawableId: Int,
    @DrawableRes val complicationStyleDrawableId: Int,
    val ambientPrimaryColor: Int,
    val ambientSecondaryColor: Int,
    val ambientBackgroundColor: Int,
    val ambientOuterElementColor: Int
) {
    companion object {
        /**
         * Converts [ColorStyleData] to [WatchFaceColorPalette].
         */
        fun convertToWatchFaceColorPalette(
            context: Context,
            activeColorStyle: ColorStyleData,
            ambientColorStyle: ColorStyleData
        ): WatchFaceColorPalette {

            return WatchFaceColorPalette(
                // Active colors
                activePrimaryColor = context.getColor(activeColorStyle.primaryColorId),
                activeSecondaryColor = context.getColor(activeColorStyle.secondaryColorId),
                activeBackgroundColor = context.getColor(activeColorStyle.backgroundColorId),
                activeOuterElementColor = context.getColor(activeColorStyle.outerElementColorId),
                // color styles
                hourPipsStyleDrawableId = activeColorStyle.hourPipsStyleDrawableId,
                secondStyleDrawableId = activeColorStyle.secondStyleDrawableId,
                appLauncherStyleDrawableId = activeColorStyle.appLauncherStyleDrawableId,
                complicationStyleDrawableId = activeColorStyle.complicationStyleDrawableId,
                // Ambient colors
                ambientPrimaryColor = context.getColor(ambientColorStyle.primaryColorId),
                ambientSecondaryColor = context.getColor(ambientColorStyle.secondaryColorId),
                ambientBackgroundColor = context.getColor(ambientColorStyle.backgroundColorId),
                ambientOuterElementColor = context.getColor(ambientColorStyle.outerElementColorId)
            )
        }
    }
}
