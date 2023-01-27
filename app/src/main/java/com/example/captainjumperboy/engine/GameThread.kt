package com.example.captainjumperboy.engine

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.example.captainjumperboy.ui.GameView

class GameThread(private var surfaceHolder: SurfaceHolder, private var gameView: GameView) : Thread() {
    private var running : Boolean = false
    private var targetFPS : Long = 60
    private var averageFPS : Long = 0

    companion object{
        private var canvas : Canvas? = null
    }

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        //variables for frame rate control
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        var totalTime: Long = 0
        var frameCount = 0
        val targetTime: Long = 1000 / targetFPS

        while (running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(canvas)
                }
            }
            catch (_: Exception){ }
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                    catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }

            //frame rate controller, @todo could move into separate class
            timeMillis = (System.nanoTime() - startTime) / 1000000
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
    }
}