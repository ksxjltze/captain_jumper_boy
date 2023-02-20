package com.stepbros.captainjumperboy.database.leaderboard

import android.provider.SyncStateContract.Helpers.update
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

//DAO stands for Data Access Object and is a Kotlin class that provides access to the data
//DAOs must either be interfaces or abstract classes

/**
 * Marks this interface as the DAO which will provide access to the data stored in the database
 */
@Dao
interface LeaderboardDao {

    //Gets all the scores in ascending order
    @Query("SELECT * FROM leaderboard_table ORDER BY score DESC")
    fun getAll(): Flow<List<Leaderboard>> //observe data changes w/ Flow
    //asynchronous flow (Flow) will allow the DAO to continuously emit data from the database without blocking the main thread

    //By default, all queries MUST be executed on a separate thread thus we need suspend functions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leaderboard : Leaderboard)

    @Update
    suspend fun update(leaderboard: Leaderboard)

    @Transaction
    suspend fun insertOrUpdate(leaderboard: Leaderboard) {
        // Insert the new item
        insert(leaderboard)

        // Get all items in descending order by score
        val items = getAll().first()

        // Update the id column for each item based on its position in the sorted order
        for (i in items.indices) {
            val item = items[i]
            item.id = i + 1
            update(item)
        }
    }

    @Query("DELETE FROM leaderboard_table")
    suspend fun deleteAll()

    @Query("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'leaderboard_table'")
    suspend fun resetPrimaryKey()
}