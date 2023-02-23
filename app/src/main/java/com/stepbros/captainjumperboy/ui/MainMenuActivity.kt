package com.stepbros.captainjumperboy.ui

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import com.stepbros.captainjumperboy.GameApplication
import com.stepbros.captainjumperboy.R

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

        val auth = Firebase.auth
        if (auth.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, FirebaseUIActivity::class.java))
            finish()
            return
        }

        val startbtn: Button = findViewById(R.id.playbtn)
        val leaderboardBtn: Button = findViewById(R.id.leaderboardBtn)
        val creditsBtn: Button = findViewById(R.id.creditsBtn)
        val playerIdText : TextView = findViewById(R.id.playerIdText)

        playerIdText.text = (application as GameApplication).auth.currentUser?.displayName ?: "GUEST"

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

        creditsBtn.setOnClickListener {

            Toast.makeText(this, "Credits!", Toast.LENGTH_SHORT).show()
            //Go to leaderboard
            val intent = Intent(this, CreditsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        AuthUI.getInstance().signOut(this)
        startActivity(Intent(this, FirebaseUIActivity::class.java))
        finish()
    }

}
