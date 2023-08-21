package com.example.remind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var everyday : Button
    private lateinit var dointhefuture : Button
    private lateinit var myideas : Button
    private lateinit var questions : Button
    private lateinit var paragraphe : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        everyday = findViewById(R.id.everyday)
        dointhefuture = findViewById(R.id.dointhefuture)
        myideas = findViewById(R.id.myideas)
        questions = findViewById(R.id.questions)
        paragraphe = findViewById(R.id.paragraphe)

        everyday.setOnClickListener {
            val theIntent = Intent(this , Everyday::class.java)
            startActivity(theIntent)
            overridePendingTransition(R.anim.slide_right , R.anim.slide_out_left)
        }
        dointhefuture.setOnClickListener {
            /*val theIntent = Intent(this , Skilles_To_improve_in_the_future::class.java)
            startActivity(theIntent)*/
            overridePendingTransition(R.anim.slide_right , R.anim.slide_out_left)
        }
        myideas.setOnClickListener {
            /*val theIntent = Intent(this , MyIdeas::class.java)
            startActivity(theIntent)*/
            overridePendingTransition(R.anim.slide_right , R.anim.slide_out_left)
        }
        questions.setOnClickListener {
            /*val theIntent = Intent(this , MyQuestions::class.java)
            startActivity(theIntent)*/
            overridePendingTransition(R.anim.slide_right , R.anim.slide_out_left)
        }
        paragraphe.setOnClickListener {
            /*val theIntent = Intent(this , MyParagraphes::class.java)
            startActivity(theIntent)*/
            overridePendingTransition(R.anim.slide_right , R.anim.slide_out_left)
        }



    }// end on create

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right)
    }
}