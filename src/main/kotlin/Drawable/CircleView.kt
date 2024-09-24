package Drawable

import CircleModel
import Extra.Coordinates
import com.raylib.Jaylib.*
import com.raylib.Raylib.Vector2

class CircleView(private var circle: CircleModel, windowWidth: Float, windowHeight: Float) : IDrawable {
    private val graphRatio = .5f
    private val dataColor = BLACK
    private val importantDataColor = RED
    private var animationStarted = false
    private val graphScale = 10f
    private val dotSize = 3f
    private val graphY = 50f
    private val btnWidth = 100f
    private val btnHeight = 75f
    private var somethingIsActive = false

    private var size = Vector2().x(windowWidth).y(windowHeight)
    private val graph = Graph(
        (size.x() - graphRatio * size.x()) / 2,
        graphY,
        graphRatio * size.x(),
        graphRatio * size.y(),
        getMaxValues(),
        dataColor
    )
    private val btn = Button(
        size.x() / 2 - btnWidth / 2,
        size.y() - btnHeight * 2,
        btnWidth,
        btnHeight,
        "Start"
    ) {
        animationStarted = true
        somethingIsActive = false
    }

    private val velocityBox = DoubleBox(
        10f,
        size.y() - btnHeight * 2,
        btnWidth,
        btnHeight,
        "Velocity"
    )

    private val radiusBox = DoubleBox(
        size.x() - btnWidth - 10f,
        size.y() - btnHeight * 2,
        btnWidth,
        btnHeight,
        "Radius"
    )

    private fun getMaxValues() : Coordinates {
        val maxX = circle.circleDiameter() * graphScale
        val ratio = size.y() / size.x()
        val maxY = maxX * ratio
        return Coordinates(maxX, maxY)
    }

    fun resize(windowWidth: Float, windowHeight: Float) {
        size = Vector2().x(windowWidth).y(windowHeight)
        graph.relocate(
            (size.x() - graphRatio * size.x()) / 2,
            graphY
        )
        graph.resize(
            graphRatio * size.x(),
            graphRatio * size.y()
        )
        btn.relocate(size.x() / 2 - btnWidth / 2, size.y() - btnHeight * 2)
        radiusBox.relocate(size.x() - btnWidth - 10f, size.y() - btnHeight * 2)
        velocityBox.relocate(10f, size.y() - btnHeight * 2)
    }

    private fun drawGraph() {
        graph.setMax(getMaxValues())
        graph.draw()
    }

    private fun drawDots() {
        for (i in 0..<circle.getDots().size step 10) {
            DrawCircleV(graph.relativeToFactualCoordinates(circle.getDots()[i]), 1f, importantDataColor)
        }
    }

    private fun drawCircle() {
        if (circle.centerPos.x > getMaxValues().x) {
            animationStarted = false
            btn.isDisabled = false
            radiusBox.isDisabled = false
            velocityBox.isDisabled = false
        }
        val circlePos = graph.relativeToFactualCoordinates(circle.centerPos)
        val dotPos = graph.relativeToFactualCoordinates(circle.dotPos)
        val actualRadius = Vector2Distance(circlePos, dotPos)
        DrawCircleLinesV(circlePos, actualRadius, dataColor)
        DrawCircleV(circlePos, dotSize, dataColor)
        DrawCircleV(dotPos, dotSize, importantDataColor)
        DrawLineV(circlePos, dotPos, dataColor)
        graph.drawMeasurement(circle.dotPos)
        if (animationStarted) {
            circle.nextFrame(GetFPS())
        }
    }

    override fun draw() {
        drawGraph()
        drawDots()
        drawCircle()
        if (animationStarted) {
            btn.isDisabled = true
            radiusBox.isDisabled = true
            velocityBox.isDisabled = true
        }
        btn.draw()
        velocityBox.draw()
        radiusBox.draw()

        if (!animationStarted) {
            if (velocityBox.active || radiusBox.active) {
                somethingIsActive = true
            } else if (somethingIsActive == true) {
                circle = CircleModel(radiusBox.getValue(), velocityBox.getValue())
            }
        }
    }
}