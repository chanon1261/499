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
import com.independent.m.mproject499.model.Score
import com.independent.m.mproject499.model.User
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        stat_page.text = "STAT FRAGMENT NO SCORE"


        stat_listening.text = "LISTENING SCORE : 0 POINT"
        stat_matching.text = "MATCHING SCORE : 0 POINT"
        stat_speaking.text = "SPEAKING SCORE : 0 POINT"

        val email = auth.currentUser!!.uid
        MainApp.database.child("users-score").orderByChild("uid")
            .equalTo(email)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                @SuppressLint("SetTextI18n")
                override fun onDataChange(p0: DataSnapshot) {

                    for (postSnapshot in p0.children) {
//                        stat_page.text = "STAT FRAGMENT " + postSnapshot.child("listening").value!!
                        postSnapshot.child("listening").value?.let {
                            it.toString().toDouble().let{ x->
                                stat_listening.text = "LISTENING SCORE : ${roundOffDecimal(x)} POINT"
                            }
                        }
                        postSnapshot.child("matching").value?.let {
                            it.toString().toDouble().let{ x->
                                stat_matching.text = "MATCHING SCORE : ${roundOffDecimal(x)} POINT"
                            }

                        }
                        postSnapshot.child("speaking").value?.let {
                            it.toString().toDouble().let{ x->
                                stat_speaking.text = "SPEAKING SCORE : ${roundOffDecimal(x)} POINT"
                            }

                        }

                    }
                    // dataSnapshot.children.mapNotNullTo(wordList) { it.getValue<WordFireBase>(WordFireBase::class.java) }
//                    val user: MutableList<Score> = mutableListOf()
//                    p0.children.mapNotNullTo(user) {
//                        it?.getValue<Score>(Score::class.java)
//                    }
//                    user?.first().let {
//                        it.listening.let { x ->
//                            stat_listening.text = "LISTENING SCORE : ${roundOffDecimal(x)} POINT"
//                        }
//                        it.matching.let { x ->
//                            stat_matching.text = "MATCHING SCORE : ${roundOffDecimal(x)} POINT"
//                        }
//                        it.matching.let { x ->
//                            stat_speaking.text = "SPEAKING SCORE : ${roundOffDecimal(x)} POINT"
//                        }
//                    }
                }

            })
    }

    fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }


}
