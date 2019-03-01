package com.example.m.mproject499.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.m.mproject499.MainActivity
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.R
import kotlinx.android.synthetic.main.app_bar_main.*

class WordsActivity : AppCompatActivity() {

    var number:Int? = null
    companion object {
        fun getStartIntent(context: Context) = Intent(context, WordsActivity::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)
        MainApp.graph.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val extras = intent.extras
        if (extras != null)
        {
            extras.getString("key")?.let {
                //title = it
                supportActionBar?.title = "DAY $it"
                number = it.toInt()
            }
        }



    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
