package com.example.m.mproject499.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.R
import kotlinx.android.synthetic.main.item_matching.view.*


class MatchingAdapter(context: Context, var choice: List<String>, var ans: String) : BaseAdapter() {
    var context: Context? = context

    override fun getCount(): Int {
        return choice.size
    }

    override fun getItem(position: Int): Any {
        return choice[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val data = this.choice[position]

        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val grid = inflator.inflate(R.layout.item_matching, null)
        grid.item_choice.text = data
        grid.setOnClickListener {
            if (ans.toLowerCase() in data) {
                grid.setBackgroundColor(
                    ContextCompat.getColor(
                        MainApp.instance.applicationContext,
                        R.color.correct
                    )
                )
                MainApp.Result.add(true)
            } else {
                MainApp.Result.add(false)
                grid.setBackgroundColor(
                    ContextCompat.getColor(
                        MainApp.instance.applicationContext,
                        R.color.fail
                    )
                )

                val count = parent?.childCount
                //Log.d("count", count.toString())
                for (i in 0 until count!!) {
                    val c = parent.getChildAt(i)
                    c.isClickable = false
                    if (ans.toLowerCase() in c.item_choice.text) {
                        c.setBackgroundColor(
                            ContextCompat.getColor(
                                MainApp.instance.applicationContext,
                                R.color.correct
                            )
                        )
                    }
                }
            }
        }


        return grid
    }


}