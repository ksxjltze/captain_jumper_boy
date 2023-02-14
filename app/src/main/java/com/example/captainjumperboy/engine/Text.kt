package com.example.captainjumperboy.engine

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.withMatrix
import com.example.captainjumperboy.engine.assets.Image
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.math.Vector2D

class Text(var paint : Paint=Paint() ,var pos:Vector2D=Vector2D(),var str:String="Some Test",var textcolor:Int=Color.BLACK) : Component() {
    override fun draw(canvas: Canvas){
        val matrix = transform.getMatrix()
        matrix.postConcat(Camera.transform.getViewMatrix()) //View * Model

        //apply transform and draw
        canvas.withMatrix(matrix) {
//            paint.setColor(Color.WHITE);
//            paint.setStyle(Paint.Style.FILL);
            canvas.drawPaint(paint);
            paint.setColor(textcolor);
            canvas.drawText(str, pos.x, pos.y, paint);
        }

    }
}
