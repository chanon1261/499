package com.example.m.mproject499

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m.mproject499.Adapter.DaysAdapter
import com.example.m.mproject499.Model.Days
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    companion object {
        fun fragment(mainActivity: MainActivity): MainFragment {
            val fragment = MainFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        main_page.text = "MAIN FRAGMENT"

        val recyclerView = day_recycle
        val adapter = DaysAdapter(generateData())
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()
        super.onViewCreated(view, savedInstanceState)


    }

    private fun generateData(): ArrayList<Days> {
        val result = ArrayList<Days>()

        for (i in 1..30) {
            val user = Days(i.toString(), " ")
            result.add(user)
        }
        return result
    }

}
