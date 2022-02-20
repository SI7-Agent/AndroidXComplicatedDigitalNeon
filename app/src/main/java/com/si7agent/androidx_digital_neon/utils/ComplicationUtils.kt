package com.si7agent.androidx_digital_neon.utils

import android.content.Context
import android.graphics.RectF
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import com.si7agent.androidx_digital_neon.R

private const val DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID = R.drawable.complication_white_style

// Unique IDs for each complication. The settings activity that supports allowing users
// to select their complication data provider requires numbers to be >= 0.
internal enum class ComplicationID(val id: Int){
    LEFT_COMPLICATION_ID(100),
    RIGHT_COMPLICATION_ID(101),
    TOP_COMPLICATION_ID(102),
    BOTTOM_COMPLICATION_ID(103),
    MIDDLE_COMPLICATION_ID(104),
}

// Information needed for complications.
// Creates bounds for the locations of both right and left complications.
// (This is the location from 0.0 - 1.0.)
internal enum class ComplicationBounds(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
){
    LEFT_COMPLICATION_BOUNDS(
        left = 0.24f,
        top = 0.69f,
        right = 0.38f,
        bottom = 0.83f
    ),
    RIGHT_COMPLICATION_BOUNDS(
        left = 0.62f,
        top = 0.69f,
        right = 0.76f,
        bottom = 0.83f
    ),
    TOP_COMPLICATION_BOUNDS(
        left = 0.43f,
        top = 0.05f,
        right = 0.57f,
        bottom = 0.19f
    ),
    BOTTOM_COMPLICATION_BOUNDS(
        left = 0.43f,
        top = 0.8f,
        right = 0.57f,
        bottom = 0.94f
    ),
    MIDDLE_COMPLICATION_BOUNDS(
        left = 0.78f,
        top = 0.43f,
        right = 0.92f,
        bottom = 0.57f
    )
}

sealed class ComplicationConfig(val id: Int, val supportedTypes: List<ComplicationType>) {
    object Left : ComplicationConfig(
        ComplicationID.LEFT_COMPLICATION_ID.id,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )
    object Right : ComplicationConfig(
        ComplicationID.RIGHT_COMPLICATION_ID.id,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )
    object Top : ComplicationConfig(
        ComplicationID.TOP_COMPLICATION_ID.id,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )
    object Bottom : ComplicationConfig(
        ComplicationID.BOTTOM_COMPLICATION_ID.id,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )
    object Middle : ComplicationConfig(
        ComplicationID.MIDDLE_COMPLICATION_ID.id,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )
}

fun createComplicationSlotManager(
    context: Context,
    currentUserStyleRepository: CurrentUserStyleRepository,
    drawableId: Int = DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID
): ComplicationSlotsManager {

    val defaultCanvasComplicationFactory =
        CanvasComplicationFactory { watchState, listener ->
            CanvasComplicationDrawable(
                ComplicationDrawable.getDrawable(context, drawableId)!!,
                watchState,
                listener
            )
        }

    val leftComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Left.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Left.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_DAY_OF_WEEK
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                ComplicationBounds.LEFT_COMPLICATION_BOUNDS.left,
                ComplicationBounds.LEFT_COMPLICATION_BOUNDS.top,
                ComplicationBounds.LEFT_COMPLICATION_BOUNDS.right,
                ComplicationBounds.LEFT_COMPLICATION_BOUNDS.bottom
            )
        )
    ).setDefaultDataSourceType(ComplicationType.SHORT_TEXT)
        .build()

    val rightComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Right.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Right.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                ComplicationBounds.RIGHT_COMPLICATION_BOUNDS.left,
                ComplicationBounds.RIGHT_COMPLICATION_BOUNDS.top,
                ComplicationBounds.RIGHT_COMPLICATION_BOUNDS.right,
                ComplicationBounds.RIGHT_COMPLICATION_BOUNDS.bottom
            )
        )
    ).setDefaultDataSourceType(ComplicationType.SHORT_TEXT)
        .build()

    val topComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Top.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                ComplicationBounds.TOP_COMPLICATION_BOUNDS.left,
                ComplicationBounds.TOP_COMPLICATION_BOUNDS.top,
                ComplicationBounds.TOP_COMPLICATION_BOUNDS.right,
                ComplicationBounds.TOP_COMPLICATION_BOUNDS.bottom
            )
        )
    ).setDefaultDataSourceType(ComplicationType.SHORT_TEXT)
        .build()

    val bottomComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Bottom.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Bottom.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                ComplicationBounds.BOTTOM_COMPLICATION_BOUNDS.left,
                ComplicationBounds.BOTTOM_COMPLICATION_BOUNDS.top,
                ComplicationBounds.BOTTOM_COMPLICATION_BOUNDS.right,
                ComplicationBounds.BOTTOM_COMPLICATION_BOUNDS.bottom
            )
        )
    ).setDefaultDataSourceType(ComplicationType.SHORT_TEXT)
        .build()

    val middleComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Middle.id,
        canvasComplicationFactory = defaultCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Middle.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                ComplicationBounds.MIDDLE_COMPLICATION_BOUNDS.left,
                ComplicationBounds.MIDDLE_COMPLICATION_BOUNDS.top,
                ComplicationBounds.MIDDLE_COMPLICATION_BOUNDS.right,
                ComplicationBounds.MIDDLE_COMPLICATION_BOUNDS.bottom
            )
        )
    ).setDefaultDataSourceType(ComplicationType.SHORT_TEXT)
        .build()

    return ComplicationSlotsManager(
        listOf(
            leftComplication,
            rightComplication,
            topComplication,
            bottomComplication,
            middleComplication
        ),
        currentUserStyleRepository
    )
}
