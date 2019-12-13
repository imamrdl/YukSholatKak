package com.fstudio.yuksholatkak

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            button = findViewById(R.id.schedule)
            button.setOnClickListener{
                startActivity(Intent(this, JadwalActivity::class.java))
            }


    }

//        //button
//        val mStartActBtn = findViewById<Button>(R.id.schedule)
//        //handle button click
//        mStartActBtn.setOnClickListener {
//            //start activity intent
//            startActivity(Intent(this@MainActivity, JadwalActivity::class.java))
//        }
//
        //button
//        val mStartActBtn1 = findViewById<Button>(R.id.feedbackbutton)
//        //handle button click
//        mStartActBtn1.setOnClickListener {
//            //start activity intent
//            startActivity(Intent(this@MainActivity, FeedbackActivity::class.java))
//        }






}