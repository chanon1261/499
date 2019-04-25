package com.independent.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.independent.m.mproject499.R
import com.independent.m.mproject499.adapter.TestAdapter
import com.independent.m.mproject499.data.Constants
import kotlinx.android.synthetic.main.activity_test_in_word.*

class TestInWordActivity : AppCompatActivity() {

    var day = -1
    var chapter = ""

    companion object {
        fun getStartIntent(context: Context) = Intent(context, TestInWordActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_in_word)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            extras.getString("key")?.let {
                day = it.toInt()
            }
            extras.getString("name")?.let {
                supportActionBar?.title = "Day $day : $it"
            }
        }


        val adapter = TestAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        test_in_word?.layoutManager = layoutManager
        test_in_word?.adapter = adapter
        test_in_word.addItemDecoration(DividerItemDecoration(test_in_word.context, DividerItemDecoration.VERTICAL))
        adapter.loadData(Constants.TEST_MODE)
        adapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
