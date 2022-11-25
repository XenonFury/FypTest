package com.example.myfyptest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myfyptest.R
import com.example.myfyptest.databinding.ProductListItemBinding
import com.example.myfyptest.menuCreator.product.Food
import com.example.myfyptest.menuCreator.product.Product
import com.example.myfyptest.menuCreator.fragments.MenuCreatorTabFragmentDirections

class ProductListAdapter(
    private var context: Context,
    private val database: HashMap<String, in Product>
) : RecyclerView.Adapter<ProductListAdapter.ItemViewHolder>(){


    private var dataset:List<Food> = database.values.toList() as List<Food>

    class ItemViewHolder(private var binding : ProductListItemBinding,val view:View) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food : Food, foodId :String, context: Context){
            binding.productIdTextView.text = foodId
            binding.productNameTextView.text = food.name
            binding.productDescriptionTextView.text = food.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        return ItemViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentId : String = database.keys.elementAt(position)
        val current = database[currentId] as Food

        holder.bind(current,currentId, context)
        holder.itemView.setOnClickListener{
            val action = MenuCreatorTabFragmentDirections.actionMenuCreatorTabFragment2ToViewProductFragment(currentId,"food")
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = dataset.size

}
