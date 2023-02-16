package com.stepbros.captainjumperboy.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.stepbros.captainjumperboy.GameApplication
import com.stepbros.captainjumperboy.R

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

        val startbtn: Button = findViewById(R.id.playbtn)
        val leaderboardBtn: Button = findViewById(R.id.leaderboardBtn)
        val playerIdText : TextView = findViewById(R.id.playerIdText)

        playerIdText.text = (application as GameApplication).user.displayName

        startbtn.setOnClickListener {
            Toast.makeText(this, "Game starting!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        leaderboardBtn.setOnClickListener {
            Toast.makeText(this, "Leaderboard!", Toast.LENGTH_SHORT).show()
            //Go to leaderboard
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }

    }
}
