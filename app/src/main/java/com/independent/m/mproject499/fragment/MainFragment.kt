package com.independent.m.mproject499

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.independent.m.mproject499.adapter.DaysAdapter
import com.independent.m.mproject499.model.Chapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_main.*
import android.support.v7.widget.DividerItemDecoration
import com.independent.m.mproject499.model.WordHistory
import com.vicpin.krealmextensions.queryAll


class MainFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var database: DatabaseReference
    private lateinit var dayList: MutableList<Chapter>


    companion object {
        fun fragment(mainActivity: MainActivity): MainFragment {
            val fragment = MainFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        database = FirebaseDatabase.getInstance().reference
        dayList = mutableListOf()

        val adapter = DaysAdapter(MainApp.instance.applicationContext)
        val layoutManager = LinearLayoutManager(MainApp.instance.applicationContext)
        day_recycle?.layoutManager = layoutManager
        day_recycle?.adapter = adapter
        day_recycle.addItemDecoration(DividerItemDecoration(day_recycle.context, DividerItemDecoration.VERTICAL))
        initChapter(adapter)
        adapter.loadData(dayList as java.util.ArrayList<Chapter>)
        adapter.notifyDataSetChanged()

        val count = WordHistory().queryAll().count()
        Log.d("WordHistory","$count")

        super.onViewCreated(view, savedInstanceState)

    }

    private fun initChapter(adapter: DaysAdapter) {
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dayList.clear()
                dataSnapshot.children.mapNotNullTo(dayList) { it.getValue<Chapter>(Chapter::class.java) }
                Log.d("SIZEE", "${dayList.size}")
                adapter.loadData(dayList as java.util.ArrayList<Chapter>)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        database.child("chapters").addValueEventListener(userListener)
    }

}
