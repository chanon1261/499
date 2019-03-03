package com.example.m.mproject499.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.m.mproject499.R

class ListeningActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, ListeningActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Listening"
    }
}
