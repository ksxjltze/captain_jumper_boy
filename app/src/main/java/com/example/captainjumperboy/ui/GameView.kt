package com.example.captainjumperboy.ui


import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.captainjumperboy.R
import com.example.captainjumperboy.game.GameThread
import com.example.captainjumperboy.game.engine.Scene
import com.example.captainjumperboy.game.engine.Sprite

class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback{
    companion object{
        private lateinit var thread: GameThread
    }

    private lateinit var sprite : Sprite
    private var scene = Scene()

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        focusable = FOCUSABLE
    }

    fun update(){

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawColor(Color.WHITE)
            scene.draw(canvas)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        sprite = Sprite(BitmapFactory.decodeResource(resources, R.drawable.bird))
        scene.getObject().addComponent(sprite)

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
