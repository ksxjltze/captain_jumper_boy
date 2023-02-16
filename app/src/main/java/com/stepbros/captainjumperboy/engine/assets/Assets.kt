package com.stepbros.captainjumperboy.engine.assets

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import com.stepbros.captainjumperboy.ui.GameView

class Assets {
    companion object{
        val targetWidth = 100
        val targetHeight = 100

        lateinit var view : GameView //this is probably bad?

        val GreenPaint : Paint = Paint().apply { color = Color.GREEN; style = Paint.Style.STROKE; strokeWidth = 3F}
        val YellowPaint : Paint = Paint().apply { color = Color.YELLOW; style = Paint.Style.STROKE; strokeWidth = 3F }
        val RedPaint : Paint = Paint().apply { color = Color.RED; style = Paint.Style.STROKE; strokeWidth = 3F }
        val DebugPaint = RedPaint

        fun getBitmap(resourceId : Int) : Bitmap{
            return BitmapFactory.decodeResource(view.resources, resourceId)
        }
    }
}