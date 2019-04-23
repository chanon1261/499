package com.independent.m.mproject499.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.independent.m.mproject499.activity.WordsActivity
import com.independent.m.mproject499.MainApp
import com.independent.m.mproject499.databinding.ItemDayBinding
import com.independent.m.mproject499.model.Chapter
import org.jetbrains.anko.toast

import java.util.*


class DaysAdapter(val context: Context) : RecyclerView.Adapter<DaysAdapter.DaysAdapterViewHolder>() {

    private var items: ArrayList<Chapter> = java.util.ArrayList()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return DaysAdapterViewHolder(
            com.independent.m.mproject499.databinding.ItemDayBinding.inflate(
                layoutInflator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DaysAdapterViewHolder, position: Int) = holder.bind(items[position])

    class DaysAdapterViewHolder(val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bind(item: Chapter) {

            val day = "Day ${item.day}:"
            binding.setVariable(BR.name, day)
            binding.setVariable(BR.eng, item.eng)
            binding.setVariable(BR.th, item.th)


            itemView.setOnClickListener {
                if (adapterPosition > 14) {
                    context.toast("coming soon....")
                } else {
                    MainApp.instance.applicationContext?.let { context ->
                        val intent = WordsActivity.getStartIntent(context)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("key", "${adapterPosition + 1}")
                        intent.putExtra("name", item.eng)
                        context.startActivity(intent)
                    }

                }
            }
        }
    }

    fun loadData(data: ArrayList<Chapter>) {
        this.items = data
        notifyDataSetChanged()
    }
}