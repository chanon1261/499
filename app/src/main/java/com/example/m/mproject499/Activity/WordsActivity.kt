package com.example.m.mproject499.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.m.mproject499.Adapter.WordsAdapter
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.Model.TestWord
import com.example.m.mproject499.Model.Words
import com.example.m.mproject499.R
import com.vicpin.krealmextensions.queryAll
import kotlinx.android.synthetic.main.activity_words.*

class WordsActivity : AppCompatActivity() {

    var number: Int? = null

    companion object {
        fun getStartIntent(context: Context) = Intent(context, WordsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)
        MainApp.graph.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val extras = intent.extras
        if (extras != null) {
            extras.getString("key")?.let {
                //title = it
                supportActionBar?.title = "DAY $it"
                number = it.toInt()
            }
        }

        val adapter = WordsAdapter(this)
        val layoutManager = LinearLayoutManager(MainApp.instance.applicationContext)
        word_recycle?.layoutManager = layoutManager
        word_recycle?.adapter = adapter
        adapter.loadDatas(generateDataWord())
        adapter.notifyDataSetChanged()


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun generateDataWord(): ArrayList<Words> {
        val result = ArrayList<Words>()

        TestWord().queryAll().let {
            for (i in it) {
                val user = i.word?.let { it1 ->
                    i.meaning?.let { it2 ->
                        i.desc_eng?.let { it3 ->
                            Words(
                                it1, it2,
                                it3, i.desc_th!!
                            )
                        }
                    }
                }
                user?.let { it1 -> result.add(it1) }
            }
        }
        return result
    }
}
