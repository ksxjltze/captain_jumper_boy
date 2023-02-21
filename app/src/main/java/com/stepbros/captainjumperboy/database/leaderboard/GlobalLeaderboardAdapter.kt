package com.stepbros.captainjumperboy.database.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.stepbros.captainjumperboy.R
import edu.singaporetech.madata.adapter.LeaderboardAdapter

class GlobalLeaderboardAdapter(
    private val options: FirebaseRecyclerOptions<Leaderboard>)
 : FirebaseRecyclerAdapter<Leaderboard, ViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return LeaderboardAdapter.LeaderboardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Leaderboard) {
        val leaderboard = options.snapshots[position]
        (holder as LeaderboardAdapter.LeaderboardViewHolder).bind(leaderboard.id.toString(), leaderboard.name, leaderboard.score.toString())
    }
 }