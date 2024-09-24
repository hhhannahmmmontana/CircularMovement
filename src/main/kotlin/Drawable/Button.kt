package Drawable

import Extra.RaylibExtra
import com.raylib.Jaylib.*
import com.raylib.Raylib

class Button(
    private val x: Float,
    private val y: Float,
    private val width: Float,
    private val height: Float,
    val text: String,
    private val onAction: () -> Unit
) {
    var backgroundColor = WHITE
    var hoverColor = GRAY
    var clickColor = DARKGRAY
    var borderColor = BLACK
    var disabledColor = GRAY
    var textColor = BLACK

    companion object {
        @JvmStatic
        private val normalState = 0
        @JvmStatic
        private val hoverState = 1
        @JvmStatic
        private val clickState = 2
    }

    var isDisabled = false
    var font = RaylibExtra.Fonts.TimesNewRoman
    var fontSize = 20f
    var strokeWidth = 2f

    private var bounds = Rectangle(x, y, width, height)

    fun relocate(x: Float, y: Float) {
        bounds = bounds.x(x).y(y) as Rectangle
    }

    private fun selectColor(state: Int): Raylib.Color {
        if (isDisabled) {
            return disabledColor
        }
        return when (state) {
            hoverState -> hoverColor
            clickState -> clickColor
            else -> backgroundColor
        }
    }

    private fun getState(): Int {
        if (CheckCollisionPointRec(GetMousePosition(), bounds)) {
            return if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
                clickState
            } else {
                hoverState
            }
        }
        return normalState
    }

    private fun drawText() {
        val textBounds = Raylib.MeasureTextEx(font, text, fontSize, 0f)
        val btnCenter = RaylibExtra.getCenter(bounds)
        val textPosition = Raylib.DrawTextEx(
            font,
            text,
            Raylib.Vector2().x(
                btnCenter.x() - textBounds.x() / 2
            ).y(
                btnCenter.y() - textBounds.y() / 2
            ),
            fontSize,
            0f,
            textColor
        )
    }

    fun draw() {
        val state = getState()
        val currentColor = selectColor(state)
        DrawRectangleRec(bounds, currentColor)
        DrawRectangleLinesEx(bounds, strokeWidth, borderColor)

        drawText()

        if (state == clickState && !isDisabled) {
            onAction()
        }
    }
}