package com.example.captainjumperboy.ui


import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Assets
import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.Sprite
import com.example.captainjumperboy.game.scenes.CaptainJumperBoy
import com.example.captainjumperboy.game.scripts.Keith

class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback{
    companion object{
        private lateinit var thread: GameThread
    }

    private var scene = CaptainJumperBoy(this)

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        focusable = FOCUSABLE
    }

    fun update(){
        scene.update()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawColor(Color.WHITE)
            scene.draw(canvas)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        //@todo maybe should move elsewhere
        run {
            Assets.view = this
            scene.start()
        }

        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        while (true) {
            try {
                thread.setRunning(false)
                thread.join()
                break
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

}
