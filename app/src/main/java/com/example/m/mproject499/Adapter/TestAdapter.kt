package com.example.m.mproject499.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.example.m.mproject499.Activity.ListeningActivity
import com.example.m.mproject499.Activity.MatchingActivity
import com.example.m.mproject499.Activity.SpeakingActivity
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.databinding.TestListBinding
import org.jetbrains.anko.toast
import java.util.*


class TestAdapter(val context: Context): RecyclerView.Adapter<TestAdapter.TestAdapterViewHolder>() {

    private var items: ArrayList<String> = java.util.ArrayList()


    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return TestAdapterViewHolder(com.example.m.mproject499.databinding.TestListBinding.inflate(layoutInflator, parent, false))
    }

    override fun onBindViewHolder(holder: TestAdapterViewHolder, position: Int) = holder.bind(items[position])

    class TestAdapterViewHolder(val binding: TestListBinding) : RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bind(item: String) {
            binding.setVariable(BR.test_name, item)
            itemView.setOnClickListener {
                itemView.setOnClickListener {
                    if(adapterPosition == 0 || adapterPosition == 1){
                        context.toast("LISTENING")
                        doStartActivity(ListeningActivity.getStartIntent(context))

                    }else if(adapterPosition == 2 || adapterPosition == 3){
                        context.toast("MATCHING")
                        doStartActivity(MatchingActivity.getStartIntent(context))
                    }else {
                        context.toast("SPEAKING")
                        doStartActivity(SpeakingActivity.getStartIntent(context))
                    }
                }
            }

        }

        private fun doStartActivity(intent:Intent) {
            MainApp.instance.applicationContext?.let { context ->
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("key", "${adapterPosition + 1}")
                context.startActivity(intent)
            }
        }
    }
    fun loadData(data: ArrayList<String>){
        this.items = data
        notifyDataSetChanged()
    }

}