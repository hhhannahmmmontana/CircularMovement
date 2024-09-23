import com.raylib.Jaylib.BLACK
import com.raylib.Jaylib.RED
import com.raylib.Raylib.*;

class CircleView(private val circle: CircleModel, windowWidth: Float, windowHeight: Float) {
    private val xRatio = 8f / 10f
    private val yRatio = 9f / 10f
    private val dataColor = BLACK
    private val importantDataColor = RED
    private val animationStarted = false
    private val graphScale = 3f

    private var size = Vector2().x(windowWidth).y(windowHeight)
    private val graph = Graph(
        (size.x() - xRatio * size.x()) / 2,
        (size.y() - yRatio * size.y()) / 2,
        xRatio * size.x(),
        xRatio * size.y(),
        circle.circleDiameter() * graphScale,
        circle.circleDiameter() * graphScale,
        dataColor
    )

    fun resize(windowWidth: Float, windowHeight: Float) {
        size = Vector2().x(windowWidth).y(windowHeight)
    }

    fun draw() {
        graph.relocate(
            (size.x() - xRatio * size.x()) / 2,
            (size.y() - yRatio * size.y()) / 2
        )
        graph.resize(
            xRatio * size.x(),
            xRatio * size.y()
        )
        graph.draw()
        val circlePos = graph.relativeToFactualCoordinates(circle.centerPos)
        DrawCircleV(circlePos, circle.radius.toFloat(), dataColor)
    }
}