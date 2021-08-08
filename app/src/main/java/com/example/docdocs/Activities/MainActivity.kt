package com.example.docdocs.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.docdocs.Models.Category
import com.example.docdocs.Models.Coordinate
import com.example.docdocs.Models.Document
import com.example.docdocs.Models.Folder
import com.example.docdocs.R
import android.os.Handler;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateToChoice()
    }




    private fun navigateToChoice(){
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this,ChoiceActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}

