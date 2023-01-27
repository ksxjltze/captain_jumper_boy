package com.example.captainjumperboy.game

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.example.captainjumperboy.ui.GameView

class GameThread(var surfaceHolder: SurfaceHolder, var gameView: GameView) : Thread() {
    private var running : Boolean = false

    companion object{
        private var canvas : Canvas? = null
    }

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        while (running) {
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
        }
    }
}