package com.example.myfyptest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfyptest.R
import com.example.myfyptest.databinding.ProductListItemBinding
import com.example.myfyptest.menuCreator.product.Food

class ProductListAdapter(
    private var context: Context,
    private val dataset: List<Food>
) : RecyclerView.Adapter<ProductListAdapter.ItemViewHolder>() {

    class ItemViewHolder(private var binding : ProductListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food : Food, context: Context){
            binding.productNameTextView.text = food.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view

        return ItemViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = dataset[position]
//        holder.itemView.setOnClickListener {
//            onItemClicked(current)
//        }
        holder.bind(current, context)
    }

    override fun getItemCount() = dataset.size
}
