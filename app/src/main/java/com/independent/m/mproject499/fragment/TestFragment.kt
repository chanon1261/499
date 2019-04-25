package com.independent.m.mproject499


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.independent.m.mproject499.adapter.TestAdapter
import com.independent.m.mproject499.data.Constants.TEST_MODE
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    companion object {
        fun fragment(mainActivity: MainActivity): TestFragment {
            val fragment = TestFragment()
            fragment.mainActivity = mainActivity
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        test_page.text = "TEST FRAGMENT"
        MainApp.chooseChapter = -1
        val adapter = activity?.applicationContext?.let { TestAdapter(it) }
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        test_recycle?.layoutManager = layoutManager
        test_recycle?.adapter = adapter
        test_recycle.addItemDecoration(DividerItemDecoration(test_recycle.context, DividerItemDecoration.VERTICAL))
        adapter?.loadData(TEST_MODE)
        adapter?.notifyDataSetChanged()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun generateDataTest(): ArrayList<String> {
        val result = ArrayList<String>()

        for (i in 1..6) {
            result.add(i.toString())
        }
        return result
    }


}
