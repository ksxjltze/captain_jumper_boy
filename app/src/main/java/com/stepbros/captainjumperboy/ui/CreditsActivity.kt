package com.stepbros.captainjumperboy.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stepbros.captainjumperboy.R



class CreditsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        val backBtn: Button = findViewById(R.id.backBtn)
        
        backBtn.setOnClickListener {
            Toast.makeText(this, "Back to Main Menu!", Toast.LENGTH_SHORT).show()
            //Go to leaderboard
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

}