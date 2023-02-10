package com.example.captainjumperboy.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.captainjumperboy.database.leaderboard.Leaderboard
import com.example.captainjumperboy.database.leaderboard.LeaderboardDao
import kotlinx.coroutines.flow.Flow

//viewModels is thr best practice to separate the part of the DAO you expose to the view into a separate class
//can also survive configuration changes

/**
 * ViewModel class of Leaderboard, it supplies the Leaderboard UI/Views with the data
 */
class LeaderboardViewModel(private val leaderboardDao: LeaderboardDao): ViewModel() {

    fun fullLeaderboard(): Flow<List<Leaderboard>> = leaderboardDao.getAll()

    init {
        Log.d("LeaderboardActivity", "LeaderboardViewModel created!")
    }

}

/**
 * This class will instantiate the ViewModel instead of having the UI do it. Thus, it should be instantiated
 * by an object that can respond to lifecycle events instead of being affected by it. Even if the Activity is
 * recreated, you'll always get the right instance of the LeaderboardViewModel.
 */
class LeaderboardViewModelFactory(private val leaderboardDao: LeaderboardDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaderboardViewModel(leaderboardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}