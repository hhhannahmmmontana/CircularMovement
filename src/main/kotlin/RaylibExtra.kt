import com.raylib.Raylib.*

sealed class RaylibExtra {
    companion object {
        @JvmStatic
        fun getCenter(vec: Rectangle): Vector2 {
            return Vector2().x(vec.x() + vec.width() / 2).y(vec.y() + vec.height() / 2)
        }
    }
    sealed class Fonts {
        companion object {
            @JvmStatic
            val TimesNewRoman = LoadFont("resources/fonts/Times New Roman.ttf")
        }
    }
}