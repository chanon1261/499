package com.example.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView
import com.example.m.mproject499.adapter.WordsAdapter
import com.example.m.mproject499.data.Constants
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.MainApp.Companion.wordsList
import com.example.m.mproject499.model.WordFireBase
import com.example.m.mproject499.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_words.*

class WordsActivity : AppCompatActivity() {

    var number: Int? = null
    private lateinit var database: DatabaseReference

    companion object {
        val TAG = "word changed: ="
        fun getStartIntent(context: Context) = Intent(context, WordsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)
        MainApp.graph.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        database = FirebaseDatabase.getInstance().reference
        //toolbar.changeToolbarFont()

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
        adapter.notifyDataSetChanged()

        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                wordsList.clear()
                dataSnapshot.children.mapNotNullTo(wordsList) { it.getValue<WordFireBase>(WordFireBase::class.java) }
                adapter.loadData(wordsList.filter { it.day == number } as java.util.ArrayList<WordFireBase>)
                Log.d(TAG, wordsList.size.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child("words").addListenerForSingleValueEvent(userListener)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun Toolbar.changeToolbarFont() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == title) {
                view.typeface = Typeface.createFromAsset(assets, Constants.FONT)
                break
            }
        }
    }

}
