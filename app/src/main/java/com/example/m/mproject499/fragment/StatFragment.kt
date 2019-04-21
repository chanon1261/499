package com.example.m.mproject499

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        stat_page.text = "STAT FRAGMENT NO SCORE"
        val email = auth.currentUser!!.uid
        MainApp.database.child("users-score").orderByChild("uid")
            .equalTo(email)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                @SuppressLint("SetTextI18n")
                override fun onDataChange(p0: DataSnapshot) {

                    for (postSnapshot in p0.children) {
//                        Log.d("fxfx", "=======" + postSnapshot.child("listening").value!!)
//                        stat_page.text = "STAT FRAGMENT " + postSnapshot.child("listening").value!!
                        postSnapshot.child("listening").value?.let {
                            stat_listening.text = "LISTENING SCORE : ${roundOffDecimal(it.toString().toDouble())} SCORE"
                        }
                        postSnapshot.child("matching").value?.let {
                            stat_matching.text = "MATCHING SCORE : ${roundOffDecimal(it.toString().toDouble())} SCORE"
                        }
                        postSnapshot.child("speaking").value?.let {
                            stat_speaking.text = "SPEAKING SCORE : ${roundOffDecimal(it.toString().toDouble())} SCORE"
                        }
                    }

                }

            })
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }


}
