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
import com.example.captainjumperboy.game.Sprite

class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback{
    companion object{
        private lateinit var thread: GameThread
    }

    private lateinit var sprite : Sprite

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
            val paint = Paint()
            paint.color = Color.rgb(250, 0, 0)
            canvas.drawRect(100F, 100F, 200F, 200F, paint)

            sprite.draw(canvas)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        sprite = Sprite(BitmapFactory.decodeResource(resources, R.drawable.bird))

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
