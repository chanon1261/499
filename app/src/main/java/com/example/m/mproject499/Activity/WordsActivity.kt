package com.example.m.mproject499.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.m.mproject499.MainActivity
import com.example.m.mproject499.R

class WordsActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, WordsActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)
    }
}
