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
import com.google.firebase.database.FirebaseDatabase
import com.stepbros.captainjumperboy.GameApplication
import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.viewmodel.LeaderboardViewModel
import com.stepbros.captainjumperboy.viewmodel.LeaderboardViewModelFactory
import edu.singaporetech.madata.adapter.LeaderboardAdapter

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var db: FirebaseDatabase

    // view binding to access view elements
    private lateinit var recyclerView : RecyclerView

    //views
    private lateinit var clearBtn : Button

    private val viewModel: LeaderboardViewModel by viewModels{
        LeaderboardViewModelFactory((application as GameApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        val switch = findViewById<SwitchCompat>(R.id.globalSwitch)
        switch.setOnCheckedChangeListener { compoundButton, b ->
            when (compoundButton.isChecked){
                true -> clearBtn.visibility = View.GONE
                false -> clearBtn.visibility = View.VISIBLE
            }
        }

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

        clearBtn = findViewById<Button>(R.id.clearBtn)
        clearBtn.setOnClickListener {
            viewModel.deleteAll()
        }
    }
}