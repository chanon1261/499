package com.independent.m.mproject499.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.independent.m.mproject499.MainApp
import com.independent.m.mproject499.R
import com.independent.m.mproject499.adapter.WordsAdapter
import com.independent.m.mproject499.model.WordFireBase
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_words.*
import android.support.v7.widget.RecyclerView
import android.view.View
import org.jetbrains.anko.toast





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

        val extras = intent.extras
        if (extras != null) {
            extras.getString("key")?.let {
                number = it.toInt()
            }
            extras.getString("name")?.let {
                supportActionBar?.title = "Day $number : $it"
            }
        }

        val adapter = WordsAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        word_recycle?.layoutManager = layoutManager
        word_recycle?.adapter = adapter
        adapter.notifyDataSetChanged()


        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val wordList: MutableList<WordFireBase> = mutableListOf()
                dataSnapshot.children.mapNotNullTo(wordList) { it.getValue<WordFireBase>(WordFireBase::class.java) }
                adapter.loadData(wordList.filter { it.day == number } as java.util.ArrayList<WordFireBase>)
                Log.d(TAG, wordList.size.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child("words").addValueEventListener(userListener)

        fabtext.setOnClickListener {

            toast("fab click")
        }

        word_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //dx horizontal distance scrolled in pixels
                //dy vertical distance scrolled in pixels
                super.onScrolled(recyclerView, dx, dy)

//                if (dy > 0 && fab.visibility == View.VISIBLE) {
//                    fab.visibility = View.GONE
//                } else if (dy < 0 && fab.visibility != View.VISIBLE) {
//                    fab.visibility = View.VISIBLE
//                    fabtext.visibility = View.VISIBLE
//                }
                if (dy < 0 && fabtext.visibility != View.VISIBLE) {
                    fabtext.visibility = View.VISIBLE
                }
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        MainApp.instance.stopTTS()
        super.onDestroy()
    }

}
