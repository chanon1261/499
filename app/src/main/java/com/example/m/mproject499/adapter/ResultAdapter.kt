package com.example.m.mproject499.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.example.m.mproject499.R
import com.example.m.mproject499.databinding.ItemResultBinding
import com.example.m.mproject499.model.WordFireBase
import kotlinx.android.synthetic.main.item_result.view.*


class ResultAdapter(val context: Context) : RecyclerView.Adapter<ResultAdapter.ResultAdapterViewHolder>() {


    private var items: MutableList<WordFireBase> = mutableListOf()
    private var result:ArrayList<Boolean> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return ResultAdapterViewHolder(
            com.example.m.mproject499.databinding.ItemResultBinding.inflate(
                layoutInflator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ResultAdapterViewHolder, position: Int) = holder.bind(items[position],result[position])

    class ResultAdapterViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bind(item: WordFireBase,result:Boolean) {
            binding.setVariable(BR.result_word, item.word)
            when(result){
                true ->{
                    itemView.result_img.setImageResource(R.drawable.ic_check)
                    itemView.result_img.setBackgroundResource(R.drawable.oval_shape)

                }
                false ->{
                    itemView.result_img.setImageResource(R.drawable.ic_close)
                    itemView.result_img.setBackgroundResource(R.drawable.oval_shape_red)
                }
            }
        }

    }

    fun loadData(data: MutableList<WordFireBase>, r: ArrayList<Boolean>) {
        this.items = data
        this.result = r
        notifyDataSetChanged()
    }


}