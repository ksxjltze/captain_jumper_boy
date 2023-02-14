package edu.singaporetech.madata.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.captainjumperboy.R
import com.example.captainjumperboy.database.leaderboard.Leaderboard
import android.content.Context
import android.util.Log

class LeaderboardAdapter(private val context: Context) :
    ListAdapter<Leaderboard, LeaderboardAdapter.LeaderboardViewHolder>(LeaderboardComparator()) {

    /**
     * Provide a reference to the views used in RecyclerView for each data item
     * Complex data items need more than one view per item, and
     * you provide access to all the views for a data item in a view holder.
     */
    class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val numberId: TextView = itemView.findViewById(R.id.leaderboardNumber)
        private val name: TextView = itemView.findViewById(R.id.leaderboardName)
        private val score: TextView = itemView.findViewById(R.id.leaderboardScore)

        fun bind(idText: String?, nameText: String?, scoreText: String?) {
            numberId.text = idText
            name.text = nameText
            score.text = scoreText
        }

        companion object {
            fun create(parent: ViewGroup): LeaderboardViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                return LeaderboardViewHolder(view)
            }
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LeaderboardViewHolder {
        return LeaderboardViewHolder.create(viewGroup)
    }

    /**
     * Replace the contents of a view with a new view item (invoked by the layout manager)
     */
    override fun onBindViewHolder(viewHolder: LeaderboardViewHolder, position: Int) {
        Log.d("LeaderboardActivity", "onBindViewHolder")
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val current = getItem(position)
        viewHolder.bind(
            context.getString(R.string.idString, current.id),
            context.getString(R.string.nameString, current.name),
            context.getString(R.string.scoreString, current.score)
        )
    }

    // Return the size of your dataset (invoked by the layout manager)

    class LeaderboardComparator : DiffUtil.ItemCallback<Leaderboard>() {
        override fun areItemsTheSame(oldItem: Leaderboard, newItem: Leaderboard): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Leaderboard, newItem: Leaderboard): Boolean {
            return (oldItem.id == newItem.id) && (oldItem.name == newItem.name) && (oldItem.score == newItem.score)
        }
    }
}
