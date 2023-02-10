package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.withMatrix
import androidx.core.view.ViewCompat
import com.example.captainjumperboy.engine.component.Component

class SpriteSheet (private val image: Bitmap, rows: Int, cols: Int) : Component()
{
    private val animation = AnimationDrawable()
    private var frameIndex = 0
    private val frames = ArrayList<Bitmap>()

    private val width: Int = image.width / cols
    private val height: Int = image.height / rows

    private var timer: Float = 0f

    init {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                frames.add(Bitmap.createBitmap(image, col * width, row * height, width, height))
            }
        }
        for (frame in frames) {
            animation.addFrame(BitmapDrawable(Assets.view.resources, frame), 100)
        }
        animation.isOneShot = false
        animation.start()
    }

    override fun draw(canvas: Canvas) {
        //super.draw(canvas)
        val frame = animation.getFrame(frameIndex)

        val matrix = transform.getMatrix()
        matrix.postConcat(Camera.transform.getMatrix()) //View * Model
        canvas.withMatrix(matrix) {
            frame.bounds.set(0, 0, 1000, 1000)
                frame.draw(canvas)
        }

        if (animation.isRunning) {
            timer += 0.5f //@todo: proper timer increment (using dt or something)
            if(timer >= 2f)
            {
                frameIndex++
                if (frameIndex >= animation.numberOfFrames) {
                    frameIndex = 0
                }
                ViewCompat.postInvalidateOnAnimation(Assets.view)
                timer = 0f
            }

        }

    }

    fun start()
    {

    }

    fun stop()
    {
        animation.stop()
    }
}