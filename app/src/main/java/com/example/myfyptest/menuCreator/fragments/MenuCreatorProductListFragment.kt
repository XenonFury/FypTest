package com.example.myfyptest.menuCreator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myfyptest.adapter.ProductListAdapter
import com.example.myfyptest.databinding.FragmentMenuCreatorProductListBinding
import com.example.myfyptest.menuCreator.product.ProductDatabase

class MenuCreatorProductListFragment : Fragment() {

    private var _binding :FragmentMenuCreatorProductListBinding? = null
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
        _binding =  FragmentMenuCreatorProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.productListRecyclerView
        val dataset = menu.getFoodDataset()
        recyclerView.adapter = context?.let { ProductListAdapter(it,dataset) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}


