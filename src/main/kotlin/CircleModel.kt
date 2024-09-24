import Extra.Coordinates
import Extra.Physics
import kotlin.math.cos
import kotlin.math.sin

class CircleModel(val radius: Double, private val velocity: Double) {
    private val frequency = Physics.GetCircleMovementFrequency(radius, velocity)
    private var angle = 180.0

    var centerPos = Coordinates(radius, radius)
        private set
    var dotPos = calculateDotPos()
        private set

    private val dots = arrayListOf(dotPos)

    private fun calculateDotPos(): Coordinates {
        val radAngle = Math.toRadians(angle)
        return Coordinates(
            centerPos.x + radius * cos(radAngle),
            centerPos.y + radius * sin(radAngle)
        )
    }

    fun init() {
        val firstDot = dots.first()
        dots.clear()
        dots.add(firstDot)
    }

    fun getDots(): ArrayList<Coordinates> {
        return dots
    }

    fun circleDiameter(): Double {
        return 2 * radius
    }

    fun nextFrame(fps: Int?) {
        val fpsFact = fps ?: 60
        centerPos = Coordinates(centerPos.x + velocity / fpsFact, centerPos.y)
        angle -= (frequency * 360 / fpsFact) % 360
        dotPos = calculateDotPos()
        dots.add(dotPos)
    }
}