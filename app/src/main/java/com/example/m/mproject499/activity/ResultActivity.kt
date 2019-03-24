package com.example.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.MainApp.Companion.History
import com.example.m.mproject499.MainApp.Companion.Result
import com.example.m.mproject499.R
import com.example.m.mproject499.adapter.ResultAdapter
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, ResultActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var score = 0
        for (it in MainApp.Result.filter { it }) {
            score += 1
        }
        title = "SCORE $score/10"

        val adapter = ResultAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        ListResult?.layoutManager = layoutManager
        ListResult?.adapter = adapter
        adapter.loadData(History, Result)
        adapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        MainApp.Result.clear()
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        Result.clear()
        super.onDestroy()
    }
}
