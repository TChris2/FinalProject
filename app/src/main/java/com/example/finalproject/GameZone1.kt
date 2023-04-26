package com.example.finalproject

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.PopupMenu
import java.util.Random
import androidx.core.content.ContentProviderCompat.requireContext

class GameZone1 : AppCompatActivity() {
    /*Map Pos
    stores information about the map
    0: Open Space
    1: Impassible
    2: Orange Crab Pos
    3: Yellow Crab Pos
    4: Snail Pos
    5: Turtle/Player Pos
    6: Goal
    */
    var MapPosArray = arrayOf<Int>(0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0, 6, 0,
                              0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 1, 0, 0, 0,
                              0, 0, 0, 3, 0, 0, 0,
                              0, 0, 0, 0, 0, 2, 1,
                              0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0, 0, 0,
                              0, 0, 0, 0, 0, 4, 0,
                              0, 5, 0, 0, 0, 0, 0,)

    var RandomNum: Int = 0

    //array movement variables
    var ArrayTPos: Int =  0
    var ArrayOCPos: Int =  0
    var ArrayYCPos: Int =  0
    var ArraySPos: Int =  0

    //orange crab move variables
    var OCMoveX: Int = 152
    var OCDirect: Int = -2

    //yellow crab move variables
    var YCMoveY: Int = 140
    var YCDirect: Int = 1

    //snail move variables
    var SMoveX: Int = 152
    var SMoveY: Int = 140

    //turtle move variables
    var TMoveX: Int = 152
    var TMoveY: Int = 138
    var MoveDirect: Int = 0
    var MoveCheck: Float = 0.0F

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_zone1)

        //player/enemies
        var Turtle = findViewById<pl.droidsonroids.gif.GifImageView>(R.id.Turtle)
        var Crab = findViewById<pl.droidsonroids.gif.GifImageView>(R.id.Crab)
        var Crab2 = findViewById<pl.droidsonroids.gif.GifImageView>(R.id.Crab2)
        var Snail = findViewById<pl.droidsonroids.gif.GifImageView>(R.id.Snail)

        //buttons
        val UpB = findViewById<Button>(R.id.UpB)
        val DownB = findViewById<Button>(R.id.DownB)
        val RightB = findViewById<Button>(R.id.RightB)
        val LeftB = findViewById<Button>(R.id.LeftB)

        //gets initial locations of things
        PosLoad()

        //sets listener to buttons
        UpB.setOnClickListener {
            MoveDirect = -1
            PlayerMove(MoveDirect, Turtle, Crab, Crab2, Snail)
        }
        DownB.setOnClickListener {
            MoveDirect = 1
            PlayerMove(MoveDirect, Turtle, Crab, Crab2, Snail)
        }
        RightB.setOnClickListener {
            MoveDirect = 2
            PlayerMove(MoveDirect, Turtle, Crab, Crab2, Snail)
        }
        LeftB.setOnClickListener {
            MoveDirect = -2
            PlayerMove(MoveDirect, Turtle, Crab, Crab2, Snail)
        }
    }

    //gets initial locations of things
    fun PosLoad() {
        for (i in MapPosArray.indices) {
            if (MapPosArray[i] == 2) {
                ArrayOCPos = i
            }
            if (MapPosArray[i] == 3) {
                ArrayYCPos = i
            }
            if (MapPosArray[i] == 4) {
                ArraySPos = i
            }
            if (MapPosArray[i] == 5) {
                ArrayTPos = i
            }
        }
    }

    //movement
    fun PlayerMove (MoveDirect: Int, Turtle: pl.droidsonroids.gif.GifImageView,
                    Crab: pl.droidsonroids.gif.GifImageView, Crab2: pl.droidsonroids.gif.GifImageView,
                    Snail: pl.droidsonroids.gif.GifImageView) {
        when (MoveDirect) {
            //down
            1 -> {
                MoveCheck = Turtle.getY() + TMoveY
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 5) {
                        //prevents crash or player hits wall
                        if (MoveCheck > 1450 || MapPosArray[i+7] == 1) {
                            break
                        }
                        //check for win
                        else if (MapPosArray[i+7] == 6) {
                            Turtle.setY(MoveCheck)
                            OCrabMove(Crab)
                            YCrabMove(Crab2)
                            SnailMove(Snail, Turtle)
                            Win()
                            break
                        }
                        //movement
                        else {
                            ArrayTPos = i+7
                            MapPosArray[i] = 0
                            Turtle.setY(MoveCheck)
                            ArrayMoveUpdate(Crab, Crab2, Snail, Turtle)
                            break
                        }
                    }
                }
            }
            //up
            -1 -> {
                MoveCheck = Turtle.getY() - TMoveY
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 5) {
                        //prevents crash or player hits wall
                        if (MoveCheck < 0 || MapPosArray[i-7] == 1) {
                            break
                        }
                        //check for win
                        else if (MapPosArray[i-7] == 6) {
                            Turtle.setY(MoveCheck)
                            OCrabMove(Crab)
                            YCrabMove(Crab2)
                            SnailMove(Snail, Turtle)
                            Win()
                            break
                        }
                        //movement
                        else {
                            ArrayTPos = i-7
                            MapPosArray[i] = 0
                            Turtle.setY(MoveCheck)
                            ArrayMoveUpdate(Crab, Crab2, Snail, Turtle)
                            break
                        }
                    }
                }
            }
            //right
            2 -> {
                MoveCheck = Turtle.getX() + TMoveX
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 5) {
                        //prevents crash or player hits wall
                        if (MoveCheck > 1000 || MapPosArray[i+1] == 1) {
                            break
                        }
                        //check for win
                        else if (MapPosArray[i+1] == 6) {
                            Turtle.setX(MoveCheck)
                            OCrabMove(Crab)
                            YCrabMove(Crab2)
                            SnailMove(Snail, Turtle)
                            Win()
                            break
                        }
                        //movement
                        else {
                            ArrayTPos = i+1
                            MapPosArray[i] = 0
                            Turtle.setX(MoveCheck)
                            ArrayMoveUpdate(Crab, Crab2, Snail, Turtle)
                            break
                        }
                    }
                }
            }
            //left
            -2 -> {
                MoveCheck = Turtle.getX() - TMoveX
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 5) {
                        //prevents crash or player hits wall
                        if (MoveCheck < 0 || MapPosArray[i-1] == 1) {
                            break
                        }
                        //check for win
                        else if (MapPosArray[i-1] == 6) {
                            Turtle.setX(MoveCheck)
                            OCrabMove(Crab)
                            YCrabMove(Crab2)
                            SnailMove(Snail, Turtle)
                            Win()
                            break
                        }
                        //movement
                        else {
                            ArrayTPos = i-1
                            MapPosArray[i] = 0
                            Turtle.setX(MoveCheck)
                            ArrayMoveUpdate(Crab, Crab2, Snail, Turtle)
                            break
                        }
                    }
                }
            }
        }
    }
    //updates position of stuff in the array
    fun ArrayMoveUpdate (Crab: pl.droidsonroids.gif.GifImageView, Crab2: pl.droidsonroids.gif.GifImageView,
                         Snail: pl.droidsonroids.gif.GifImageView, Turtle: pl.droidsonroids.gif.GifImageView) {
        OCrabMove(Crab)
        YCrabMove(Crab2)
        SnailMove(Snail, Turtle)
        for (i in MapPosArray.indices) {
            if (i == ArrayTPos) {
                MapPosArray[i] = 5
            }
            else if (i == ArrayOCPos) {
                MapPosArray[i] = 2
            }
            else if (i == ArrayYCPos) {
                MapPosArray[i] = 3
            }
            else if (i ==  ArraySPos) {
                MapPosArray[i] = 4
            }
        }
        LossCheck()
    }
    //orange crab movement
    fun OCrabMove (Crab: pl.droidsonroids.gif.GifImageView) {
        when (OCDirect) {
            //right
            2 -> {
                MoveCheck = Crab.getX() + OCMoveX
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 2) {
                        //prevents crash or it reaches a wall
                        if (MoveCheck > 1000 || MapPosArray[i+1] == 1) {
                            OCDirect = -2
                            break
                        }
                        //movement
                        else {
                            ArrayOCPos = i+1
                            MapPosArray[i] = 0
                            Crab.setX(MoveCheck)
                            break
                        }
                    }
                }
            }
            //left
            -2 -> {
                MoveCheck = Crab.getX() - OCMoveX
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 2) {
                        //prevents crash or it reaches a wall
                        if (MoveCheck < 0 || MapPosArray[i-1] == 1) {
                            OCDirect = 2
                            break
                        }
                        //movement
                        else {
                            ArrayOCPos = i-1
                            MapPosArray[i] = 0
                            Crab.setX(MoveCheck)
                            break
                        }
                    }
                }
            }
        }
    }
    //yellow crab movement
    fun YCrabMove (Crab2: pl.droidsonroids.gif.GifImageView) {
        when (YCDirect) {
            //up
            -1 -> {
                MoveCheck = Crab2.getY() - YCMoveY
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 3) {
                        //prevents crash or it reaches a wall
                        if (MoveCheck < 0 || MapPosArray[i-7] == 1) {
                            YCDirect = 1
                            break
                        }
                        //movement
                        else {
                            ArrayYCPos = i-7
                            MapPosArray[i] = 0
                            Crab2.setY(MoveCheck)
                            break
                        }
                    }
                }
            }
            //down
            1 -> {
                MoveCheck = Crab2.getY() + YCMoveY
                for (i in MapPosArray.indices) {
                    if (MapPosArray[i] == 3) {
                        //prevents crash or it reaches a wall
                        if (MoveCheck > 1450 || MapPosArray[i+7] == 1) {
                            YCDirect = -1
                            break
                        }
                        //movement
                        else {
                            ArrayYCPos = i+7
                            MapPosArray[i] = 0
                            Crab2.setY(MoveCheck)
                            break
                        }
                    }
                }
            }
        }
    }
    //snail movement
    fun SnailMove (Snail: pl.droidsonroids.gif.GifImageView, Turtle: pl.droidsonroids.gif.GifImageView) {
        if (Snail.getX() > Turtle.getX() && Snail.getY() > Turtle.getY()) {
            RandomNum = Random().nextInt(2)
            when (RandomNum) {
                //left
                1 -> { SnailLeft(Snail) }
                //up
                0 -> { SnailUp(Snail) }
            }
        }
        else if (Snail.getX() < Turtle.getX() && Snail.getY() < Turtle.getY()) {
            RandomNum = Random().nextInt(2)
            when (RandomNum) {
                //right
                1 -> { SnailRight(Snail) }
                //down
                0 -> { SnailDown(Snail) }
            }
        }
        else if (Snail.getX() > Turtle.getX() && Snail.getY() < Turtle.getY()) {
            RandomNum = Random().nextInt(2)
            when (RandomNum) {
                //left
                1 -> { SnailLeft(Snail) }
                //down
                0 -> { SnailDown(Snail) }
            }
        }
        else if (Snail.getX() < Turtle.getX() && Snail.getY() > Turtle.getY()) {
            RandomNum = Random().nextInt(2)
            when (RandomNum) {
                //right
                1 -> { SnailRight(Snail) }
                //up
                0 -> { SnailUp(Snail) }
            }
        }
    }
    fun SnailUp (Snail: pl.droidsonroids.gif.GifImageView) {
        MoveCheck = Snail.getY() - SMoveY
        for (i in MapPosArray.indices) {
            if (MapPosArray[i] == 4) {
                //prevents crash or it reaches a wall
                if (MoveCheck < 0 || MapPosArray[i-7] == 1) {
                    break
                }
                //movement
                else {
                    ArraySPos = i-7
                    MapPosArray[i] = 0
                    Snail.setY(MoveCheck)
                    break
                }
            }
        }
    }
    fun SnailDown (Snail: pl.droidsonroids.gif.GifImageView) {
        MoveCheck = Snail.getY() + SMoveY
        for (i in MapPosArray.indices) {
            if (MapPosArray[i] == 3) {
                //prevents crash or it reaches a wall
                if (MoveCheck > 1450 || MapPosArray[i+7] == 1) {
                    break
                }
                //movement
                else {
                    ArraySPos = i+7
                    MapPosArray[i] = 0
                    Snail.setY(MoveCheck)
                    break
                }
            }
        }
    }
    fun SnailLeft (Snail: pl.droidsonroids.gif.GifImageView) {
        MoveCheck = Snail.getX() - SMoveX
        for (i in MapPosArray.indices) {
            if (MapPosArray[i] == 4) {
                //prevents crash or it reaches a wall
                if (MoveCheck < 0 || MapPosArray[i-1] == 1) {
                    break
                }
                //movement
                else {
                    ArraySPos = i-1
                    MapPosArray[i] = 0
                    Snail.setX(MoveCheck)
                    break
                }
            }
        }
    }
    fun SnailRight (Snail: pl.droidsonroids.gif.GifImageView) {
        MoveCheck = Snail.getX() + SMoveX
        for (i in MapPosArray.indices) {
            if (MapPosArray[i] == 4) {
                //prevents crash or it reaches a wall
                if (MoveCheck > 1000 || MapPosArray[i+1] == 1) {
                    break
                }
                //movement
                else {
                    ArraySPos = i+1
                    MapPosArray[i] = 0
                    Snail.setX(MoveCheck)
                    break
                }
            }
        }
    }
    //checks if player is touching foe
    fun LossCheck () {
        if (ArrayTPos == ArrayOCPos || ArrayTPos == ArrayYCPos || ArrayTPos == ArraySPos) {
            startActivity(Intent(this,LoseScreen::class.java))
        }
    }
    //when the player reaches the goal
    fun Win () {
        startActivity(Intent(this,WinScreen::class.java))
    }
}