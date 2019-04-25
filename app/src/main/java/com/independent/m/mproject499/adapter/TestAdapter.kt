package com.independent.m.mproject499.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.independent.m.mproject499.activity.ListeningActivity
import com.independent.m.mproject499.activity.MatchingActivity
import com.independent.m.mproject499.activity.SpeakingActivity
import com.independent.m.mproject499.databinding.ItemTestBinding


class TestAdapter(val context: Context) : RecyclerView.Adapter<TestAdapter.TestAdapterViewHolder>() {

    private var items: Array<String> = emptyArray()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return TestAdapterViewHolder(
            com.independent.m.mproject499.databinding.ItemTestBinding.inflate(
                layoutInflator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TestAdapterViewHolder, position: Int) = holder.bind(items[position])

    class TestAdapterViewHolder(val binding: ItemTestBinding) : RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bind(item: String) {
            binding.setVariable(BR.test_name, item)
            itemView.setOnClickListener {
                if (adapterPosition == 0 || adapterPosition == 1) {
                    val intent = ListeningActivity.getStartIntent(context)
                    intent.putExtra("key", "$adapterPosition")
                    Log.d("modex position","$adapterPosition")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else if (adapterPosition == 2) {
                    val intent = MatchingActivity.getStartIntent(context)
                    intent.putExtra("key", "$adapterPosition")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } else {
                    val intent = SpeakingActivity.getStartIntent(context)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("key", "$adapterPosition")
                    context.startActivity(intent)
                }
            }
        }
    }

    fun loadData(data: Array<String>) {
        this.items = data
        notifyDataSetChanged()
    }

}