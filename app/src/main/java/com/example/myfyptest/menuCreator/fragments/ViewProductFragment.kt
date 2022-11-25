package com.example.myfyptest.menuCreator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myfyptest.R
import com.example.myfyptest.databinding.FragmentViewProductBinding
import com.example.myfyptest.menuCreator.product.Food
import com.example.myfyptest.menuCreator.product.Modifier
import com.example.myfyptest.menuCreator.product.ProductDatabase
import com.example.myfyptest.model.AddMenuViewModel
import java.util.*

class ViewProductFragment : Fragment() {

    private var _binding: FragmentViewProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel : AddMenuViewModel by viewModels()

    private val menu = ProductDatabase
    private var _food : Food? = null
    private val food : Food
        get() = _food!!
    private var _modifier : Modifier? = null
    private val modifier : Modifier
        get() = _modifier!!
    private lateinit var type : String
    private lateinit var productId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewProductBinding.inflate(inflater, container, false)
         arguments?.let {
             type = ViewProductFragmentArgs.fromBundle(it).type
            productId = ViewProductFragmentArgs.fromBundle(it).fId
         }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (type.lowercase()){
            "food" -> {
                _food = menu.getProduct(productId) as Food
                loadFoodData()
            }
            "modifier" -> {
                _modifier = menu.getModifier(productId)
                loadModifierData()
            }
            else -> {}
        }

    }

    private fun loadModifierData() {
        binding.nameTextView.text = modifier.name
        addModifierRow(productId)
        val inflater = layoutInflater.inflate(R.layout.row_view_modifier, null)
        for (i in modifier.modifierList){
            addModifierItemRow(i,inflater)
        }
    }

    private fun addModifierRow(key: String){
        val inflater = layoutInflater.inflate(R.layout.row_view_modifier, null)
        inflater.findViewById<TextView>(R.id.modifier_name_textView).text = menu.getModifier(key)?.name.toString()

        for (i in menu.getModifier(key)?.modifierList!!)
            addModifierItemRow(i,inflater)
        binding.modifiersListLayout.addView(inflater)
    }

    private fun addModifierItemRow(key : String, view:View){
        val modifierItemInflater = layoutInflater.inflate(R.layout.row_modifier_item,null)
        modifierItemInflater.findViewById<TextView>(R.id.modifier_item_name_textview).text = menu.getModifierItem(key)?.name.toString()
        modifierItemInflater.findViewById<TextView>(R.id.modifier_item_price_textview).text = menu.getModifierItem(key)?.price.toString()
        view.findViewById<LinearLayout>(R.id.modifier_item_layout).addView(modifierItemInflater)
    }

    private fun loadFoodData(){
        val inflater = layoutInflater.inflate(R.layout.food_view_component,null)
        binding.foodComponentLayout.addView(inflater)

        binding.nameTextView.text = food.name
        inflater.findViewById<TextView>(R.id.description_textview).text = food.description
        inflater.findViewById<TextView>(R.id.price_textview).text = food.price.toString()
        if (food.isModifiable){
            for(i in food.modifierList!!){
                addModifierRow(i)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}