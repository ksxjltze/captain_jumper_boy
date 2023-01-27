package com.example.happybirthday

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

        val startbtn: Button = findViewById(R.id.playbtn)

        startbtn.setOnClickListener {
            Toast.makeText(this, "Game starting!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PhysicsTestActivity::class.java)
            startActivity(intent)
        }

    }
}
