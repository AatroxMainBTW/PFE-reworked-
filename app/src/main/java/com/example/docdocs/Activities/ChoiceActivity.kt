package com.example.docdocs.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.docdocs.R

class ChoiceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

    }

     fun navigateToMainMenu(v: View){
        val intent = Intent(this,MainMenuActivity::class.java)
        startActivity(intent)
    }
}