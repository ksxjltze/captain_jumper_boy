package com.stepbros.captainjumperboy.database.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.stepbros.captainjumperboy.R
import edu.singaporetech.madata.adapter.LeaderboardAdapter

class GlobalLeaderboardAdapter(
    private val options: FirebaseRecyclerOptions<Highscore>)
 : FirebaseRecyclerAdapter<Highscore, LeaderboardAdapter.LeaderboardViewHolder>(options){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardAdapter.LeaderboardViewHolder {
       return LeaderboardAdapter.LeaderboardViewHolder.create(parent)
    }

    override fun onBindViewHolder(
        holder: LeaderboardAdapter.LeaderboardViewHolder,
        position: Int,
        model: Highscore
    ) {
        val leaderboard = options.snapshots.reversed()[position] //probably really expensive lol
        holder.bind((position + 1).toString(), leaderboard.name, leaderboard.score.toString())
    }

 }