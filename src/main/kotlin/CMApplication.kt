import com.raylib.Jaylib.RAYWHITE
import com.raylib.Raylib.*;

fun main() {
    SetConfigFlags(FLAG_WINDOW_RESIZABLE)
    InitWindow(1280, 720, "Движение по окружности")
    SetTargetFPS(60)
    val circle = CircleModel(10.0, 10.0)
    val circleView = CircleView(circle, GetScreenWidth().toFloat(), GetScreenHeight().toFloat())
    while (!WindowShouldClose()) {
        BeginDrawing()
        ClearBackground(RAYWHITE)
        circleView.resize(GetScreenWidth().toFloat(), GetScreenHeight().toFloat())
        circleView.draw()
        circle.nextFrame(GetFPS())
        EndDrawing()
    }

    CloseWindow()
}