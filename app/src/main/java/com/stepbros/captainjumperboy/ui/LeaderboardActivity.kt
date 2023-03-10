package com.stepbros.captainjumperboy.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.stepbros.captainjumperboy.GameApplication
import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.database.leaderboard.GlobalLeaderboardAdapter
import com.stepbros.captainjumperboy.database.leaderboard.Highscore
import com.stepbros.captainjumperboy.database.leaderboard.Leaderboard
import com.stepbros.captainjumperboy.viewmodel.LeaderboardViewModel
import com.stepbros.captainjumperboy.viewmodel.LeaderboardViewModelFactory
import edu.singaporetech.madata.adapter.LeaderboardAdapter

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var db: FirebaseDatabase

    // view binding to access view elements
    private lateinit var recyclerView : RecyclerView

    //views
    private lateinit var clearBtn : Button

    //adapters
    private val localAdapter = LeaderboardAdapter(this)
    private lateinit var globalAdapter : GlobalLeaderboardAdapter

    private val viewModel: LeaderboardViewModel by viewModels{
        LeaderboardViewModelFactory((application as GameApplication).repository)
    }

    public override fun onPause() {
        globalAdapter.stopListening()
        super.onPause()
    }
    override fun onResume() {
        super.onResume()
        globalAdapter.startListening()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        db = Firebase.database
        val scoresRef = db.reference.child(SCORES_CHILD).orderByChild("score")

        val options = FirebaseRecyclerOptions.Builder<Highscore>()
            .setQuery(scoresRef, Highscore::class.java)
            .build()
        globalAdapter = GlobalLeaderboardAdapter(options)

        val switch = findViewById<SwitchCompat>(R.id.globalSwitch)
        switch.setOnCheckedChangeListener { compoundButton, b ->
            when (compoundButton.isChecked){
                true -> {
                    clearBtn.visibility = View.GONE
                    recyclerView.adapter = globalAdapter
                }
                false -> {
                    clearBtn.visibility = View.VISIBLE
                    recyclerView.adapter = localAdapter
                }
            }
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // call the method addItemDecoration with the
        // recyclerView instance and add default Item Divider
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        )
        recyclerView.adapter = localAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allScores.observe(this, Observer { scores ->
            // Update the cached copy of the digits in the adapter.
            scores?.let { localAdapter.submitList(it) }
        })

        val backBtn: Button = findViewById(R.id.backBtn2)

        backBtn.setOnClickListener {
            Toast.makeText(this, "Back to Main Menu!", Toast.LENGTH_SHORT).show()
            //Go to leaderboard
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        clearBtn = findViewById<Button>(R.id.clearBtn)
        clearBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmation")
            builder.setMessage("Are you sure you want to clear the leaderboard? (This cannot be undone!)")
            builder.setPositiveButton("Yes") { dialog, which ->
                viewModel.deleteAll() //clears the database
            }
            builder.setNegativeButton("No") { dialog, which ->
                // Do nothing
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    companion object{
        const val SCORES_CHILD = "scores"
    }
}