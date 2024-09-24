package Drawable

import Extra.RaylibExtra
import com.raylib.Jaylib
import com.raylib.Jaylib.*
import com.raylib.Raylib
import java.awt.RenderingHints.Key

class DoubleBox(
    private var x: Float,
    private var y: Float,
    private var width: Float,
    private var height: Float,
    var placeholder: String
): IDrawable {
    private var dotPlaced = false
    private var bounds = Jaylib.Rectangle(x, y, width, height)
    private var text = ""
    var active = false
    var strokeWidth = 2f
    var font = RaylibExtra.Fonts.TimesNewRoman
    var fontSize = 15f
    var isDisabled = false

    var backgroundColor = WHITE
    var hoverColor = GRAY
    var disabledColor = GRAY
    var activeColor = WHITE
    var borderColor = BLACK
    var textColor = BLACK

    companion object {
        @JvmStatic
        private val normalState = 0
        @JvmStatic
        private val hoverState = 1
        @JvmStatic
        private val activeState = 2
        @JvmStatic
        private val disabledState = 3
    }

    private fun getState(): Int {
        if (isDisabled) {
            return disabledState
        }
        if (IsKeyPressed(KEY_ENTER)) {
            active = false
        }
        if (!CheckCollisionPointRec(GetMousePosition(), bounds)) {
            if (IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
                active = false
            }
            if (!active) {
                return normalState
            }
        }
        if (active || IsMouseButtonDown(MOUSE_BUTTON_LEFT)) {
            active = true
            return activeState
        }
        return hoverState
    }

    private fun drawText() {
        val textBounds = Raylib.MeasureTextEx(font, text, fontSize, 0f)
        val boxCenter = RaylibExtra.getCenter(bounds)
        val textPosition = Raylib.DrawTextEx(
            font,
            if (active && !isDisabled) text + '_' else if (text.isEmpty()) placeholder else text,
            Raylib.Vector2().x(
                bounds.x() + 5f
            ).y(
                boxCenter.y() - textBounds.y() / 2
            ),
            fontSize,
            0f,
            textColor
        )
    }

    private fun selectColor(state: Int): Raylib.Color {
        return when (state) {
            disabledState -> disabledColor
            hoverState -> hoverColor
            activeState -> activeColor
            else -> backgroundColor
        }
    }

    fun getValue(): Double {
        if (text.isEmpty()) {
            return 1.0
        }
        if (text.last() == '.') {
            text += '0'
        }
        return text.toDouble()
    }

    fun relocate(x: Float, y: Float) {
        bounds = bounds.x(x).y(y) as Rectangle
    }

    @Override
    override fun draw() {
        val state = getState()
        val currentColor = selectColor(state)
        DrawRectangleRec(bounds, currentColor)
        DrawRectangleLinesEx(bounds, strokeWidth, borderColor)
        if (active) {
            val key = GetCharPressed()
            if ((key >= KEY_ZERO) && (key <= KEY_NINE)) {
                text += key.toChar()
            } else if (key == KEY_PERIOD && !dotPlaced && text.isNotEmpty()) {
                dotPlaced = true
                text += key.toChar()
            } else if (IsKeyPressed(KEY_BACKSPACE) && text.isNotEmpty()) {
                if (text.last().code == KEY_PERIOD) {
                    dotPlaced = false
                }
                text = text.dropLast(1)
            }
        }
        drawText()
    }
}