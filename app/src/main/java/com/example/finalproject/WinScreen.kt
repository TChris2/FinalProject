package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WinScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_screen)

        val BHome = findViewById<Button>(R.id.BHome)

        //takes user back to the Level Select/Options.kt
        BHome.setOnClickListener {
            startActivity(Intent(this,Options::class.java))
        }
    }
}