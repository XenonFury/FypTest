package com.example.myfyptest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myfyptest.R
import com.example.myfyptest.databinding.ProductListItemBinding
import com.example.myfyptest.menuCreator.fragments.MenuCreatorTabFragmentDirections
import com.example.myfyptest.menuCreator.product.Modifier

class ModifierListAdapter(
    private var context: Context,
    private val database: HashMap<String,Modifier>
) : RecyclerView.Adapter<ModifierListAdapter.ItemViewHolder>(){


    private var dataset:List<Modifier> = database.values.toList()

    class ItemViewHolder(private var binding : ProductListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(modifier: Modifier, modifierId :String, context: Context){
            binding.productIdTextView.text = modifierId
            binding.productNameTextView.text = modifier.name
            binding.productDescriptionTextView.text = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        return ItemViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentId : String = database.keys.elementAt(position)
        val current = database[currentId]

        holder.bind(current!!,currentId, context)
        holder.itemView.setOnClickListener{

            val action = MenuCreatorTabFragmentDirections.actionMenuCreatorTabFragment2ToViewProductFragment(currentId,"modifier")
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = dataset.size


}
