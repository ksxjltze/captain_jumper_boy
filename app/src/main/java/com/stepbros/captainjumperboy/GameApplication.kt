package com.stepbros.captainjumperboy

import android.app.Application
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.stepbros.captainjumperboy.database.LeaderboardRepository
import com.stepbros.captainjumperboy.database.LeaderboardRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Override the Application class to initialise only one instance of the database and repository in our app.
 * Then they will just be retrieved from the Application whenever they're needed
 */
class GameApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var auth : FirebaseAuth
    var highscore = 0

    /**
     * Initialise database and repository by lazy (only when they are needed)
     */
    val database by lazy { LeaderboardRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { LeaderboardRepository(database.leaderboardDao()) }
}