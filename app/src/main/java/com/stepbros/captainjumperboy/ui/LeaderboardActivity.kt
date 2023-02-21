package com.stepbros.captainjumperboy.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        db = Firebase.database
        val scoresRef = db.reference.child(SCORES_CHILD)

        val leaderboard = Leaderboard(0, "Bryan Koh", 2000000)
        scoresRef.push().setValue(leaderboard)

        val options = FirebaseRecyclerOptions.Builder<Leaderboard>()
            .setQuery(scoresRef, Leaderboard::class.java)
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

        clearBtn = findViewById<Button>(R.id.clearBtn)
        clearBtn.setOnClickListener {
            viewModel.deleteAll()
        }
    }

    companion object{
        const val SCORES_CHILD = "scores"
    }
}