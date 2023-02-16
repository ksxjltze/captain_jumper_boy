package com.stepbros.captainjumperboy.database.leaderboard

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//DAO stands for Data Access Object and is a Kotlin class that provides access to the data
//DAOs must either be interfaces or abstract classes

/**
 * Marks this interface as the DAO which will provide access to the data stored in the database
 */
@Dao
interface LeaderboardDao {

    //Gets all the scores in ascending order
    @Query("SELECT * FROM leaderboard_table ORDER BY id ASC")
    fun getAll(): Flow<List<Leaderboard>> //observe data changes w/ Flow
    //asynchronous flow (Flow) will allow the DAO to continuously emit data from the database without blocking the main thread

    //By default, all queries MUST be executed on a separate thread thus we need suspend functions
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(leaderboard : Leaderboard)

    @Query("DELETE FROM leaderboard_table")
    suspend fun deleteAll()
}