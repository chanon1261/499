package com.example.m.mproject499.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.m.mproject499.Model.Days
import com.example.m.mproject499.R
import java.util.*


class DaysAdapter(private var items: ArrayList<Days>): RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val userDto = items[position]
        holder.txtName?.text = userDto.name
        holder.txtComment?.text = userDto.comment
    }

    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(p0.context)
            .inflate(com.example.m.mproject499.R.layout.days_list, p0, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        private val context: Context? = null
        var txtName: TextView? = null
        var txtComment: TextView? = null
        var view:LinearLayout? = null

        init {
            this.txtName = row.findViewById(R.id.txtName)
            this.txtComment = row.findViewById(R.id.txtComment)
            this.view = row.findViewById(R.id.days_view)
            row.setOnClickListener {
                Log.d("test","clicked $position" )
            }
        }
    }
}