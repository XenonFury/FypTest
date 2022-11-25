package com.example.myfyptest.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myfyptest.menuCreator.fragments.MenuCreatorModifierListFragment
import com.example.myfyptest.menuCreator.fragments.MenuCreatorProductListFragment

class MenuCreatorViewPagerAdapter(activity: Fragment) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> MenuCreatorProductListFragment()
            1 -> MenuCreatorModifierListFragment()
            else -> MenuCreatorProductListFragment()
        }
    }

}