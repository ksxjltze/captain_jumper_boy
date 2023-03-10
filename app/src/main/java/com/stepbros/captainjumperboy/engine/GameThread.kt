package com.stepbros.captainjumperboy.engine

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import com.stepbros.captainjumperboy.GameApplication
import com.stepbros.captainjumperboy.database.leaderboard.Highscore
import com.stepbros.captainjumperboy.database.leaderboard.Leaderboard
import com.stepbros.captainjumperboy.engine.GameThread.Companion.deltaTime
import com.stepbros.captainjumperboy.engine.GameThread.Companion.game
import com.stepbros.captainjumperboy.math.Transform
import com.stepbros.captainjumperboy.ui.GameView
import com.stepbros.captainjumperboy.ui.LeaderboardActivity
import com.stepbros.captainjumperboy.ui.MainMenuActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class GameThread(private var surfaceHolder: SurfaceHolder, private var gameView: GameView) : Thread() {
    private var running : Boolean = false
    private var isExit = false
    private var targetFPS : Long = 60
    private var averageFPS : Long = 0

    private var scope = CoroutineScope(Dispatchers.IO)

    companion object{
        private var canvas : Canvas? = null
        lateinit var game : GameThread
        var deltaTime : Float = 0F

        fun exit(){
            Input.touchEvent = false
            game.setRunning(false)
            game.isExit = true
        }

        fun saveScoreLocal(score : Int){
            val app = (game.gameView.context.applicationContext as GameApplication)
            var name = app.auth.currentUser?.displayName ?: "GUEST"
            if (name.isEmpty())
                name = "GUEST"

            val leaderboard = Leaderboard(0, name, score)
            game.scope.launch {
                app.repository.insert(leaderboard)
            }
        }

        fun saveHighscore(score : Int){
            val app = (game.gameView.context.applicationContext as GameApplication)
            val db = Firebase.database

            val user = app.auth.currentUser
            if (user != null){
                val scoresRef = db.reference.child(LeaderboardActivity.SCORES_CHILD)
                val name = user.displayName ?: return

                if (name.isEmpty())
                    return

                val highscore = Highscore()
                highscore.name = name
                highscore.score = score
                highscore.userId = user.uid

                val query = scoresRef.orderByChild("userId").equalTo(user.uid)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //find first matching entry (matches user uid)
                        var entry : DataSnapshot? = null
                        for (child in snapshot.children){
                            val userIdAny = child.child("userId").value ?: continue
                            val userId = userIdAny as String
                            if (userId != user.uid)
                                continue

                            entry = child
                            break
                        }

                        //no such entry, create new
                        if (entry == null){
                            scoresRef.push().setValue(highscore)
                            return
                        }

                        //fetch latest high score
                        val value = entry.child("score").value
                        if (value != null){
                            app.highscore = (value as Long).toInt()
                        }

                        if (score <= app.highscore) //score is not high score
                            return

                        //update high score
                        entry.ref.setValue(highscore)

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.i("TEST", "KABOOM")
                    }
                })

            }
        }

    }

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    /**
     * Runs our game logic and game loop on a GameThread
     */
    override fun run() {
        //variables for frame rate control
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        var totalTime: Long = 0
        var frameCount = 0
        val targetTime: Long = 1000 / targetFPS

        Camera.transform = Transform()
        game = this

        //main game loop
        while (running) {
            startTime = System.nanoTime()
            canvas = null
            //@todo game state manager?
            //update game logic and render to canvas
            try {
                canvas = surfaceHolder.lockCanvas() //prevents more than 1 thread from drawing onto canvas in surface
                synchronized(surfaceHolder) {
                    //UPDATE
                    gameView.update()

                    //RESET INPUT
                    Input.touchEvent = false

                    //RENDER
                    gameView.draw(canvas)
                }
            }
            catch (_: Exception){ }
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas) //like swapping frame buffers i guess?
                    }
                    catch (e : Exception){
                        e.printStackTrace()
                    }
                }
            }

            //frame rate controller, @todo could move into separate class
            timeMillis = (System.nanoTime() - startTime) / 1000000
            deltaTime = ((System.nanoTime() - startTime).toFloat() / 1000000) / 1000 //dt in seconds
            waitTime = targetTime - timeMillis

            try {
                sleep(waitTime)
            } catch (_: Exception) { }

            totalTime += System.nanoTime() - startTime
            frameCount++

            if (frameCount.toLong() == targetFPS) {
                averageFPS = 1000 / (totalTime / frameCount / 1000000)
                frameCount = 0
                totalTime = 0
            }
        }

        if (isExit)
            gameView.context.startActivity(Intent(gameView.context, MainMenuActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TOP))
    }
}