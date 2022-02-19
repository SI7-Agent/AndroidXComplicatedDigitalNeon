package com.si7agent.androidx_digital_neon

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.graphics.withRotation
import androidx.core.graphics.withScale
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.DrawMode
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyle
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.si7agent.androidx_digital_neon.data.watchface.ColorStyleData
import com.si7agent.androidx_digital_neon.data.watchface.FontStyleData
import com.si7agent.androidx_digital_neon.data.watchface.WatchFaceColorPalette.Companion.convertToWatchFaceColorPalette
import com.si7agent.androidx_digital_neon.data.watchface.WatchFaceData
import com.si7agent.androidx_digital_neon.utils.COLOR_STYLE_SETTING
import com.si7agent.androidx_digital_neon.utils.HOUR_PIPS_STYLE_SETTING
import com.si7agent.androidx_digital_neon.utils.FONT_STYLE_SETTING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZonedDateTime

private const val FRAME_PERIOD_MS_DEFAULT: Long = 16L

class DigitalNeonRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int
) : Renderer.CanvasRenderer(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT
) {
    private val scope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var watchFaceData: WatchFaceData = WatchFaceData()

    private val inflater: LayoutInflater = context.getSystemService(LayoutInflater::class.java)
    private val watchLayout = inflater.inflate(R.layout.watchface_view, null)
    private val display: Display = context.getSystemService(WindowManager::class.java).defaultDisplay

    init{
        val displaySize = Point(display.width, display.height)
        val specW = View.MeasureSpec.makeMeasureSpec(displaySize.x, View.MeasureSpec.EXACTLY)
        val specH = View.MeasureSpec.makeMeasureSpec(displaySize.y, View.MeasureSpec.EXACTLY)
        watchLayout.measure(specW, specH)
        watchLayout.layout(0, 0, watchLayout.measuredWidth, watchLayout.measuredHeight)
    }

    private var watchFaceColors = convertToWatchFaceColorPalette(
        context,
        watchFaceData.activeColorStyle,
        watchFaceData.ambientColorStyle
    )

    private val outerElementPaint = Paint().apply {
        isAntiAlias = true
    }

    private var doRedrawPips: Boolean = true

    init {
        scope.launch {
            currentUserStyleRepository.userStyle.collect { userStyle ->
                updateWatchFaceData(userStyle)
            }
        }
    }

    private fun updateWatchFaceData(userStyle: UserStyle) {
        Log.d(TAG, "updateWatchFace(): $userStyle")

        var newWatchFaceData: WatchFaceData = watchFaceData

        for (options in userStyle) {
            when (options.key.id.toString()) {
                COLOR_STYLE_SETTING -> {
                    val listOption = options.value as
                            UserStyleSetting.ListUserStyleSetting.ListOption

                    newWatchFaceData = newWatchFaceData.copy(
                        activeColorStyle = ColorStyleData.getColorStyleConfig(
                            listOption.id.toString()
                        )
                    )

                    doRedrawPips = true
                }
                HOUR_PIPS_STYLE_SETTING -> {
                    val booleanValue = options.value as
                            UserStyleSetting.BooleanUserStyleSetting.BooleanOption

                    newWatchFaceData = newWatchFaceData.copy(
                        drawHourPips = booleanValue.value
                    )
                }
                FONT_STYLE_SETTING -> {
                    val listOption = options.value as
                            UserStyleSetting.ListUserStyleSetting.ListOption

                    newWatchFaceData = newWatchFaceData.copy(
                        activeFontStyle = FontStyleData.getFontStyleConfig(
                            listOption.id.toString()
                        )
                    )
                }
            }
        }

        if (watchFaceData != newWatchFaceData) {
            watchFaceData = newWatchFaceData

            watchFaceColors = convertToWatchFaceColorPalette(
                context,
                watchFaceData.activeColorStyle,
                watchFaceData.ambientColorStyle
            )

            for ((_, complication) in complicationSlotsManager.complicationSlots) {
                ComplicationDrawable.getDrawable(
                    context,
                    watchFaceColors.complicationStyleDrawableId
                )?.let {
                    (complication.renderer as CanvasComplicationDrawable).drawable = it
                }
            }
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        scope.cancel("DigitalNeonRenderer scope clear() request")
        super.onDestroy()
    }

    override fun renderHighlightLayer(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
        canvas.drawColor(renderParameters.highlightLayer!!.backgroundTint)

        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.renderHighlightLayer(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    override fun render(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
        val backgroundColor = if (renderParameters.drawMode == DrawMode.AMBIENT) {
            watchFaceColors.ambientBackgroundColor
        } else {
            watchFaceColors.activeBackgroundColor
        }

        canvas.drawColor(backgroundColor)

        // CanvasComplicationDrawable already obeys rendererParameters.
        drawComplications(canvas, zonedDateTime)

//        if (renderParameters.watchFaceLayers.contains(WatchFaceLayer.COMPLICATIONS_OVERLAY)) {
//            setTime(canvas, bounds, zonedDateTime)
//        }
//
        if (renderParameters.drawMode == DrawMode.INTERACTIVE &&
            renderParameters.watchFaceLayers.contains(WatchFaceLayer.BASE) &&
            doRedrawPips && watchFaceData.drawHourPips
        ) {
            setHourPips(watchFaceColors.hourPipsStyleDrawableId)
        }

        watchLayout?.draw(canvas)
    }

    // ----- All drawing functions -----
    private fun drawComplications(canvas: Canvas, zonedDateTime: ZonedDateTime) {
        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.render(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    private fun setHourPips(
        pipDrawableId: Int,
    ) {
        val watch = BitmapFactory.decodeResource(context.resources, pipDrawableId)

        val watchZero = watchLayout.findViewById<ImageView>(R.id.watchZeroImageView)
        watchZero.setImageBitmap(watch)

        val watchThirty = watchLayout.findViewById<ImageView>(R.id.watchThirtyImageView)
        watchThirty.setImageBitmap(watch)

        val watchSixty = watchLayout.findViewById<ImageView>(R.id.watchSixtyImageView)
        watchSixty.setImageBitmap(watch)

        doRedrawPips = false
    }

    companion object {
        private const val TAG = "DigitalNeonRenderer"
    }
}
