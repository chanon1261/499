package com.independent.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.independent.m.mproject499.MainApp
import com.independent.m.mproject499.R
import com.independent.m.mproject499.adapter.TestAdapter
import com.independent.m.mproject499.data.Constants

class TestActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, TestActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val adapter = TestAdapter(MainApp.instance.applicationContext)
        val layoutManager = LinearLayoutManager(MainApp.instance.applicationContext)
        test_in_word?.layoutManager = layoutManager
        test_in_word?.adapter = adapter
        test_in_word.addItemDecoration(DividerItemDecoration(test_in_word.context, DividerItemDecoration.VERTICAL))
        adapter?.loadData(Constants.TEST_MODE)
        adapter?.notifyDataSetChanged()
    }
}
