import com.raylib.Raylib.*

class Graph(x: Float, y: Float, width: Float, height: Float, private var maxValue: Double, private val color: Color) {
    private val axisWidth = 2f
    private val measurementWidth = 2f
    private val measurementLength = 5f
    private val xTextPadding = 5f
    private val yTextPadding = -15f
    private val graphPadding = 10f

    private val font = RaylibExtra.Fonts.TimesNewRoman
    private val fontSize = 15f

    private var graphBounds = Rectangle().x(x).y(y).width(width - graphPadding).height(height - graphPadding)

    private fun drawAxis() {
        DrawLineEx(
            Vector2().x(graphBounds.x()).y(graphBounds.y() + graphBounds.height()),
            Vector2().x(graphBounds.x() + graphBounds.width()).y(graphBounds.y() + graphBounds.height()),
            axisWidth,
            color
        )
        DrawLineEx(
            Vector2().x(graphBounds.x()).y(graphBounds.y()),
            Vector2().x(graphBounds.x()).y(graphBounds.y() + graphBounds.height()),
            axisWidth,
            color
        )
        val initCoordinates = Vector2().x(maxValue).y(maxValue)
        val initPos = relativeToFactualCoordinates(maxValue)
        drawMeasurement(maxValue)
    }

    fun relativeToFactualLength(value: Double): Float {

    }

    fun relativeToFactualCoordinates(coordinates: Coordinates): Vector2 {
        val ratio = coordinates.x / max.x
        return Vector2().x(
            graphBounds.x() + (graphBounds.width() * xRatio).toFloat()
        ).y(
            graphBounds.y() + graphBounds.height() - (graphBounds.height() * yRatio).toFloat()
        )
    }

    private fun drawMeasurement(coordinates: Coordinates) {
        val pos = relativeToFactualCoordinates(coordinates)
        DrawLineEx(
            Vector2().x(pos.x()).y(graphBounds.y() + graphBounds.height()),
            Vector2().x(pos.x()).y(graphBounds.y() + graphBounds.height() + measurementLength),
            measurementWidth,
            color
        )
        DrawLineEx(
            Vector2().x(graphBounds.x() - measurementLength).y(pos.y()),
            Vector2().x(graphBounds.x()).y(pos.y()),
            measurementWidth,
            color
        )

        val xText = String.format("%.3f", coordinates.x)
        val xTextBound = MeasureTextEx(font, xText, fontSize, 0f)
        DrawTextEx(
            font,
            xText,
            Vector2().x(pos.x() - xTextBound.x() / 2)
                .y(graphBounds.y() + graphBounds.height() + measurementLength + yTextPadding + xTextBound.y()),
            fontSize,
            0f,
            color
        )

        val yText = String.format("%.3f", coordinates.y)
        val yTextBound = MeasureTextEx(font, yText, fontSize, 0f)
        DrawTextEx(
            font,
            yText,
            Vector2().x(graphBounds.x() - xTextPadding - measurementLength - yTextBound.x())
                .y(pos.y() - yTextBound.y() / 2),
            fontSize,
            0f,
            color
        )
    }

    fun resize(width: Float, height: Float) {
        graphBounds = graphBounds.width(width - graphPadding).height(height - graphPadding)
    }

    fun relocate(x: Float, y: Float) {
        graphBounds = graphBounds.x(x).y(y)
    }

    fun setMax(x: Double, y: Double) {
        max = Coordinates(x, y)
    }

    fun draw() {
        drawAxis()
    }
}