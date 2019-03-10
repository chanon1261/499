package com.example.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.R


class SpeakingActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, SpeakingActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking)
        MainApp.graph.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Speaking"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}