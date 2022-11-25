package com.example.myfyptest.menuCreator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfyptest.R
import com.example.myfyptest.adapter.ModifierListAdapter
import com.example.myfyptest.adapter.ProductListAdapter
import com.example.myfyptest.databinding.FragmentMenuCreatorModifierListBinding
import com.example.myfyptest.databinding.FragmentMenuCreatorProductListBinding
import com.example.myfyptest.menuCreator.product.ProductDatabase

class MenuCreatorModifierListFragment : Fragment() {

    private var _binding : FragmentMenuCreatorModifierListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val menu = ProductDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentMenuCreatorModifierListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.modifierListRecyclerView
        val dataset = menu.getModifierDataset()
        recyclerView.adapter = context?.let { ModifierListAdapter(it,dataset) }

    }

}