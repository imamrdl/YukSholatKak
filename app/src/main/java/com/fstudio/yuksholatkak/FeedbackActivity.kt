package com.fstudio.yuksholatkak

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_feedback.*
import java.lang.Exception

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Feedback"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        sendEmailBtn.setOnClickListener {
            val penerima = penerimaEt.text.toString().trim()
            val subject = subjectEt.text.toString().trim()
            val feedback = feedbackEt.text.toString().trim()

            emailbutton(penerima, subject, feedback)

        }
    }

    private fun emailbutton(penerima: String, subject: String, feedback: String) {

        val mIntent = Intent(Intent.ACTION_SEND)

        mIntent.data = Uri.parse("mailto: ")
        mIntent.type = "text/plain"

        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(penerima))

        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)

        mIntent.putExtra(Intent.EXTRA_TEXT, feedback)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception) {
            //if any thing goes wrong for example no email client application or any exception
            // get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}