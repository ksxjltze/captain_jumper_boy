package com.example.captainjumperboy.ui

import android.app.Activity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set fullscreen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowsInsetsController : WindowInsetsControllerCompat = WindowInsetsControllerCompat(window, window.decorView)
        windowsInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        setContentView(GameView(this))

    }
}