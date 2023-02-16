package com.stepbros.captainjumperboy.database.leaderboard

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity for Room to recognize this class as something that can be used to define database tables
//association between the class and the database using Kotlin annotations

/**
 * This represents that the Leaderboard class can be represented as a database table
 */
@Entity(tableName = "leaderboard_table")
data class Leaderboard(
    //Properties
    //Room will ultimately use these properties to both create the table and instantiate objects from rows in the database.
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "score") val score: Int
)