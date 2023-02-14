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

class Text(var pos:Vector2D=Vector2D(),var str:String="Some Test",var size:Float=5F) : Component() {
    override var layer = Layer.UI
    var paint : Paint=Paint()
    var textcolor:Int=Color.RED
    override fun draw(canvas: Canvas){
        val matrix = transform.getMatrix()
        matrix.postConcat(Camera.transform.getViewMatrix()) //View * Model

        //apply transform and draw
        canvas.withMatrix(matrix) {
            //canvas.drawPaint(paint);
            paint.setTextSize(size);
            paint.textAlign = Paint.Align.CENTER
            paint.setColor(textcolor);
            canvas.drawText(str, pos.x, pos.y, paint);
        }

    }

    override fun draw(renderer: Renderer){
        renderer.enqueue(this)
    }
}
