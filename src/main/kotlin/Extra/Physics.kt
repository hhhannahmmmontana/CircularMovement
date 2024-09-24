package Extra

sealed class Physics {
    companion object {
        @JvmStatic
        fun GetCircleMovementFrequency(radius: Double, velocity: Double): Double {
            return velocity / (2 * Math.PI * radius)
        }
    }
}