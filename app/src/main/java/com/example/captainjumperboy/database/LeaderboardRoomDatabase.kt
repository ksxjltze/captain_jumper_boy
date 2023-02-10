package com.example.captainjumperboy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.captainjumperboy.database.leaderboard.Leaderboard
import com.example.captainjumperboy.database.leaderboard.LeaderboardDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * This class represents the Room database clas, it must be abstract and extends RoomDatabase. It is the
 * database layer on top of an SQLite database. It takes care of mundane tasks that you used to handle
 * with an SQLiteOpenHelper and uses the DAO to issue queries to its database. It provides
 * compile-time checks of SQLite statements.
 */
@Database(entities = arrayOf(Leaderboard::class), version = 1, exportSchema = false)
public abstract class LeaderboardRoomDatabase : RoomDatabase() {
    /**
     * The database exposes DAOs through an abstract "getter" method for each @Dao.
     */
    abstract fun leaderboardDao(): LeaderboardDao

    /**
     * Make a singleton, to prevent having multiple instances of the database opened at the same time.
     */
    companion object {
        @Volatile //Volatile means writes to this field are immediately made visible to other threads
        private var INSTANCE: LeaderboardRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): LeaderboardRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LeaderboardRoomDatabase::class.java,
                    "leaderboard_database"
                )
                    .addCallback(LeaderboardRoomDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * This class overrides the callbacks of RoomDatabase such as onCreate() in the
     * event we want to prepopulate it with some highscores at the start or make it empty
     */
    private class LeaderboardRoomDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        //populate the database upon creation of a new Database if you want to
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.leaderboardDao())
                }
            }
        }

        /**
         * For now we just populate it by clearing everything
         */
        suspend fun populateDatabase(leaderboardDao: LeaderboardDao) {
            //Starts the database empty!
            leaderboardDao.deleteAll()
            //TODO: Add some default leaderboard scores at the start if we want
        }
    }

}