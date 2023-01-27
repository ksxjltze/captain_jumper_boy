package com.example.happybirthday

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jawnnypoo.physicslayout.Physics
import com.jawnnypoo.physicslayout.PhysicsConfig
import com.jawnnypoo.physicslayout.Shape

class PhysicsTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testphys)

        val circleView = findViewById<TextView>(R.id.text2)
        val config = PhysicsConfig(
            shape = Shape.CIRCLE
        )
        Physics.setPhysicsConfig(circleView, config)
    }
}
