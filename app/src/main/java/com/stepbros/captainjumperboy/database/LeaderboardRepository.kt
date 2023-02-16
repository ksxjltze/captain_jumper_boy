package com.stepbros.captainjumperboy.database

import androidx.annotation.WorkerThread
import com.stepbros.captainjumperboy.database.leaderboard.Leaderboard
import com.stepbros.captainjumperboy.database.leaderboard.LeaderboardDao
import kotlinx.coroutines.flow.Flow

/**
 * This is the repository class that abstracts access to multiple data sources, if we want to retrieve
 * from a cloud source can do it here. Repositories are meant to mediate between different data sources.
 * The DAO is passed into the repository constructor as opposed to the whole database as it contains all
 * the read/write methods.
 */
class LeaderboardRepository(private val leaderboardDao: LeaderboardDao) {

    /**
     * Room executes all queries on a separate thread. Observed Flow will
     * notify the observer when the data has changed.
     */
    val allScores: Flow<List<Leaderboard>> = leaderboardDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(leaderboard: Leaderboard) { //suspend tells the compiler that this needs to be called from a coroutine or suspending function.
        leaderboardDao.insert(leaderboard)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() { //suspend tells the compiler that this needs to be called from a coroutine or suspending function.
        leaderboardDao.deleteAll()
    }
}