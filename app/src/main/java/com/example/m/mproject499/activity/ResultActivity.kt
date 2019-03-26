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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_result.*
import com.google.firebase.database.DataSnapshot
import android.util.Log
import com.example.m.mproject499.model.Score
import java.util.HashMap


class ResultActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var flag_l: Boolean = false
    private var flag_s: Boolean = false
    private var flag_m: Boolean = false

    private var score1: Double = 0.0
    private var score2: Double = 0.0
    private var score3: Double = 0.0
    private var s = 0

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

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val uid = auth.currentUser!!.uid

        database.child("users-score").orderByChild("uid")
            .equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("fxfx", "======= ERROR")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (postSnapshot in p0.children) {
                        Log.d("fxfx", "=======" + postSnapshot.child("uid").value!!)
//                        Log.d("fxfx", "=======" + postSnapshot.child("username").value!!)
//                        if ((postSnapshot.child("status").value!! as Boolean)) {
//                            val score1: Int = postSnapshot.child("listening").value!! as Int
//                            writeNewScore(uid, (score + score1) / 2.0, 0.0, 0.0, true)
//                        } else {
//                            writeNewScore(uid, score.toDouble(), 0.0, 0.0, true)
//                        }

                        postSnapshot.child("flag_l").value?.let {
                            flag_l = it as Boolean
                        }

                        postSnapshot.child("flag_s").value?.let {
                            flag_s = it as Boolean
                        }

                        postSnapshot.child("flag_m").value?.let {
                            flag_s = it as Boolean
                        }

                        postSnapshot.child("listening").value?.let {
                            score1 = it.toString().toDouble()
                        }
                        postSnapshot.child("speaking").value?.let {
                            score2 = it.toString().toDouble()
                        }
                        postSnapshot.child("matching").value?.let {
                            score3 = it.toString().toDouble()
                        }




                        if (!flag_l) {
                            flag_l = true
                            writeNewScore(uid, score.toDouble(), score2, score3)
                        } else {
                            postSnapshot.child("listening").value?.let {
                                val s = it.toString()
                                Log.d("fxfx", "=======  $it")
                                writeNewScore(uid, (score.toDouble() + s.toDouble()) / 2, score2, score3)
                            }
                        }
                    }

                }

            })


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

    private fun writeNewScore(userId: String, listening: Double, speaking: Double, matching: Double) {
        val score = Score(userId, listening, speaking, matching, flag_l, flag_s, flag_m)
        val childUpdates = HashMap<String, Any>()
        childUpdates["/users-score/$userId"] = score.toMap()
        database.updateChildren(childUpdates)
    }


}
