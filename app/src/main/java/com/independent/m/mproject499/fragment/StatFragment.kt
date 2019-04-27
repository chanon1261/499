package com.independent.m.mproject499

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_stat.*
import java.math.RoundingMode
import java.text.DecimalFormat


class StatFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    var score_1: Double = 0.0
    var score_2: Double = 0.0
    var score_3: Double = 0.0

    companion object {
        fun fragment(mainActivity: MainActivity): StatFragment {
            val fragment = StatFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stat, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        val uid = auth.currentUser!!.uid

        stat_page.text = "STAT FRAGMENT NO SCORE"
        database.child("users-score").orderByChild("uid")
            .equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("fxfx", "======= ERROR")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (postSnapshot in p0.children) {
                        Log.d("fxfx", "======= " + postSnapshot.child("uid").value!!)

                        postSnapshot.child("listening").value?.let {
                            score_1 = it.toString().toDouble()
                        }
                        postSnapshot.child("speaking").value?.let {
                            score_2 = it.toString().toDouble()
                        }
                        postSnapshot.child("matching").value?.let {
                            score_3 = it.toString().toDouble()
                        }
                    }

                    stat_listening.text = "LISTENING SCORE : ${roundOffDecimal(score_1)} POINT"
                    stat_matching.text = "MATCHING SCORE :  ${roundOffDecimal(score_3)} POINT"
                    stat_speaking.text = "SPEAKING SCORE :  ${roundOffDecimal(score_2)} POINT"
                }
            })
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }


}
