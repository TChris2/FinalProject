package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val StartB = findViewById<Button>(R.id.StartB)
        StartB.setOnClickListener {
            val Intent = Intent(this,Options::class.java)
            startActivity(Intent)
        }
    }
}