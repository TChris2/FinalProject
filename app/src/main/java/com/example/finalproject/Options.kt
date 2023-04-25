package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class Options : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val BHelp = findViewById<Button>(R.id.BHelp)
        val BBack = findViewById<Button>(R.id.BBack)
        val Lv1 = findViewById<ImageButton>(R.id.lv1)
        val Lv2 = findViewById<ImageButton>(R.id.lv2)
        val Lv3 = findViewById<ImageButton>(R.id.lv3)
        BHelp.setOnClickListener {
            startActivity(Intent(this,Help::class.java))
        }
        BBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        Lv1.setOnClickListener {
            val Intent = Intent(this,GameZone1::class.java)
            startActivity(Intent)
        }
        Lv2.setOnClickListener {
            val Intent = Intent(this,GameZone2::class.java)
            startActivity(Intent)
        }
        Lv3.setOnClickListener {
            val Intent = Intent(this,GameZone3::class.java)
            startActivity(Intent)
        }
    }
}