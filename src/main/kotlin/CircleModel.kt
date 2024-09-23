import kotlin.math.cos
import kotlin.math.sin

class CircleModel(val radius: Double, private val velocity: Double) {
    var centerPos = Coordinates(radius, radius)
        private set
    var dotPos = calculateDotPos()
        private set

    private val dots = arrayListOf(dotPos)
    private val frequency = Physics.GetCircleMovementFrequency(radius, velocity)
    private var angle = 180.0

    private fun calculateDotPos(): Coordinates {
        val radAngle = Math.toRadians(angle)
        return Coordinates(
            centerPos.x + radius * cos(radAngle),
            centerPos.y + radius * sin(radAngle)
        )
    }

    fun getDots(): ArrayList<Coordinates> {
        return dots
    }

    fun circleDiameter(): Double {
        return 2 * radius
    }

    fun nextFrame(fps: Int) {
        centerPos = Coordinates(centerPos.x + velocity / fps, centerPos.y)
        angle += (frequency * 360 / fps) % 360
        dotPos = calculateDotPos()
        dots.add(dotPos)
    }
}