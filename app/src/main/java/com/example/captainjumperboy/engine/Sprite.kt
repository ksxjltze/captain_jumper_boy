package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.withMatrix
import androidx.core.view.ViewCompat.postInvalidateOnAnimation
import com.example.captainjumperboy.engine.component.Component

class Sprite(var image : Bitmap) : Component() {
    override fun draw(canvas: Canvas){
        val matrix = transform.getMatrix()
        matrix.postConcat(Camera.transform.getMatrix()) //View * Model

        //apply transform and draw
        canvas.withMatrix(matrix)
        {
            canvas.drawBitmap(image, -image.width/2F, -image.height/2F, null) //draw centered in canvas to apply transform correctly
        }

    }
}

class Spritesheet (val image: Bitmap, rows: Int, cols: Int) : Component()
{
    private val animation = AnimationDrawable()
    private var frameIndex = 0
    val frames = ArrayList<Bitmap>()
    val width: Int = image.width / cols
    val height: Int = image.height / rows
    var timer: Float = 0f

    //Scales all images imported to be a unit square of 100x100
    init {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val matrix = Matrix()
                matrix.setScale(100F /width, 100F / height)
                frames.add(
                    Bitmap.createBitmap(image, col * width, row * height, width, height, matrix, true
                    ))
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
            timer += 0.5f
            if(timer >= 2f)
            {
                frameIndex++
                if (frameIndex >= animation.numberOfFrames) {
                    frameIndex = 0
                }
                postInvalidateOnAnimation(Assets.view)
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