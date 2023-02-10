package com.example.captainjumperboy.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.captainjumperboy.database.LeaderboardRepository
import com.example.captainjumperboy.database.leaderboard.Leaderboard
import kotlinx.coroutines.launch

//viewModels is thr best practice to separate the part of the DAO you expose to the view into a separate class
//can also survive configuration changes

/**
 * ViewModel class of Leaderboard, it supplies the Leaderboard UI/Views with the data
 */
class LeaderboardViewModel(private val repository: LeaderboardRepository): ViewModel() {

    /**
     * Accessing database by converting Flow to LiveData. Can now put an observer on the data
     * (instead of polling for changes) and only update the UI when the data actually changes.
     * Repository is completely separated from the UI through the ViewModel.
     */
    val allScores: LiveData<List<Leaderboard>> = repository.allScores.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(leaderboard: Leaderboard) = viewModelScope.launch {
        repository.insert(leaderboard) //the implementation of insert() is encapsulated from the UI
    }

    /**
     * Launching a new coroutine to delete all data in a non-blocking way
     */
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll() //the implementation of deleteAll() is encapsulated from the UI
    }

    init {
        Log.d("LeaderboardActivity", "LeaderboardViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("LeaderboardActivity", "LeaderboardViewModel destroyed!")
    }

}

/**
 * This class will instantiate the ViewModel instead of having the UI do it. Thus, it should be instantiated
 * by an object that can respond to lifecycle events instead of being affected by it. Even if the Activity is
 * recreated, you'll always get the right instance of the LeaderboardViewModel.
 */
class LeaderboardViewModelFactory(private val repository: LeaderboardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaderboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}