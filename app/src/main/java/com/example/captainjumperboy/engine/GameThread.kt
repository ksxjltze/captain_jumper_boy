package com.example.captainjumperboy.engine

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Canvas
import android.view.SurfaceHolder
import com.example.captainjumperboy.GameApplication
import com.example.captainjumperboy.database.leaderboard.Leaderboard
import com.example.captainjumperboy.math.Transform
import com.example.captainjumperboy.ui.GameView
import com.example.captainjumperboy.ui.MainMenuActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameThread(private var surfaceHolder: SurfaceHolder, private var gameView: GameView) : Thread() {
    private var running : Boolean = false
    private var isExit = false
    private var targetFPS : Long = 60
    private var averageFPS : Long = 0

    private var scope = CoroutineScope(Dispatchers.IO)

    companion object{
        private var canvas : Canvas? = null
        lateinit var game : GameThread
        var deltaTime : Float = 0F

        fun exit(){
            game.setRunning(false)
            game.isExit = true
        }

        fun saveScoreLocal(score : Int){
            val leaderboard = Leaderboard(0, "score", score)
            game.scope.launch {
                (game.gameView.context.applicationContext as GameApplication)
                    .repository.insert(leaderboard)
            }
        }

    }

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    /**
     * Runs our game logic and game loop on a GameThread
     */
    override fun run() {
        //variables for frame rate control
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        var totalTime: Long = 0
        var frameCount = 0
        val targetTime: Long = 1000 / targetFPS

        Camera.transform = Transform()
        game = this

        //main game loop
        while (running) {
            startTime = System.nanoTime()
            canvas = null
            //@todo game state manager?
            //update game logic and render to canvas
            try {
                canvas = surfaceHolder.lockCanvas() //prevents more than 1 thread from drawing onto canvas in surface
                synchronized(surfaceHolder) {
                    //UPDATE
                    gameView.update()

                    //RESET INPUT
                    Input.touchEvent = false

                    //RENDER
                    gameView.draw(canvas)
                }
            }
            catch (_: Exception){ }
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas) //like swapping frame buffers i guess?
                    }
                    catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }

            //frame rate controller, @todo could move into separate class
            timeMillis = (System.nanoTime() - startTime) / 1000000
            deltaTime = ((System.nanoTime() - startTime).toFloat() / 1000000) / 1000 //dt in seconds
            waitTime = targetTime - timeMillis

            try {
                sleep(waitTime)
            } catch (_: Exception) { }

            totalTime += System.nanoTime() - startTime
            frameCount++

            if (frameCount.toLong() == targetFPS) {
                averageFPS = 1000 / (totalTime / frameCount / 1000000)
                frameCount = 0
                totalTime = 0
            }
        }

        if (isExit)
            gameView.context.startActivity(Intent(gameView.context, MainMenuActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TOP))
    }
}