package com.example.captainjumperboy

import android.app.Application
import com.example.captainjumperboy.database.LeaderboardRepository
import com.example.captainjumperboy.database.LeaderboardRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Override the Application class to initialise only one instance of the database and repository in our app.
 * Then they will just be retrieved from the Application whenever they're needed
 */
class GameApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    /**
     * Initialise database and repository by lazy (only when they are needed)
     */
    val database by lazy { LeaderboardRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { LeaderboardRepository(database.leaderboardDao()) }
}