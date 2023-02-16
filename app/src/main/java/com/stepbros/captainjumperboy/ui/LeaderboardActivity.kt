package com.stepbros.captainjumperboy.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stepbros.captainjumperboy.GameApplication
import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.viewmodel.LeaderboardViewModel
import com.stepbros.captainjumperboy.viewmodel.LeaderboardViewModelFactory
import edu.singaporetech.madata.adapter.LeaderboardAdapter

class LeaderboardActivity : AppCompatActivity() {
    // view binding to access view elements
    private lateinit var recyclerView : RecyclerView

    private val viewModel: LeaderboardViewModel by viewModels{
        LeaderboardViewModelFactory((application as GameApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // call the method addItemDecoration with the
        // recyclerView instance and add default Item Divider
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        )
        val adapter = LeaderboardAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.allScores.observe(this, Observer { scores ->
            // Update the cached copy of the digits in the adapter.
            scores?.let { adapter.submitList(it) }
        })

        val clearBtn = findViewById<Button>(R.id.clearBtn)
        clearBtn.setOnClickListener {
            viewModel.deleteAll()
        }
    }
}