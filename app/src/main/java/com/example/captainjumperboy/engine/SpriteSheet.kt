package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.graphics.withMatrix
import androidx.core.view.ViewCompat
import com.example.captainjumperboy.engine.assets.Assets
import com.example.captainjumperboy.engine.component.Component

class SpriteSheet (resourceId : Int, rows: Int, cols: Int) : Component()
{
    private val animation = AnimationDrawable()
    private var frameIndex = 0
    private val frames = ArrayList<Bitmap>()
    private var isInit = false
    private val image = Assets.getBitmap(resourceId)
    private val width: Int = image.width / cols
    private val height: Int = image.height / rows

    private var timer: Double = 0.0

    init {
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                frames.add(Bitmap.createBitmap(image, col * width, row * height, width, height))
            }
        }
        for (frame in frames) {
            animation.addFrame(BitmapDrawable(Assets.view.resources, frame), 100)
        }
        isInit = true
    }

    override fun draw(renderer: Renderer){
        renderer.enqueue(this)
    }

    override fun draw(canvas: Canvas) {
        //super.draw(canvas)
        if(isInit)
        {
            val frame = animation.getFrame(frameIndex)

            val matrix = transform.getMatrix()
            matrix.postConcat(Camera.transform.getViewMatrix()) //View * Model
            canvas.withMatrix(matrix) {
                val xOffset = -Assets.targetWidth/2
                val yOffset = -Assets.targetHeight/2
                frame.bounds.set(xOffset, yOffset, Assets.targetWidth + xOffset, Assets.targetHeight + yOffset) //kinda scuffed, @todo: maybe find a better method
                //frame.bounds.set(0, 0, Assets.targetWidth, Assets.targetHeight)
                frame.draw(canvas)
            }

            if (animation.isRunning) {

                val now = System.nanoTime()
                val dt = (now - lastTime) / 1.0e9 // Convert nanoseconds to seconds
                lastTime = now
                timer += dt

                if(timer >= 0.1)
                {
                    frameIndex++
                    if (frameIndex >= animation.numberOfFrames) {
                        frameIndex = 0
                    }
                    ViewCompat.postInvalidateOnAnimation(Assets.view)
                    timer = 0.0
                }

            }
        }

    }

    fun start()
    {
        animation.start()
    }

    fun stop()
    {
        animation.stop()
    }

    fun playonLoop()
    {
        animation.isOneShot = false
    }

    fun playOnce()
    {
        animation.isOneShot = true
    }


    companion object {
        private var lastTime = System.nanoTime()
    }
}