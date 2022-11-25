package com.example.myfyptest

import com.example.myfyptest.R
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myfyptest.adapter.MenuCreatorViewPagerAdapter
import com.example.myfyptest.databinding.ActivityMenuCreatorBinding
import com.example.myfyptest.menuCreator.fragments.AddNewItemDialogFragment
import com.google.android.material.tabs.TabLayout

class MenuCreatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuCreatorBinding
    private lateinit var appBarConfiguration : AppBarConfiguration

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }
    private var fabClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        binding.toolbarLayout.title = title

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.menu_creator_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController)


        binding.fabAddFood.setOnClickListener {
            showDialog()
        }
        binding.fab.setOnClickListener {
            onAddButtonClicked()
        }

    }

    private fun onAddButtonClicked(){
        setAnimation(fabClicked)
        setVisibility(fabClicked)
        setClickable(fabClicked)
        fabClicked = !fabClicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked){
            binding.fab.startAnimation(rotateOpen)
            binding.fabAddFood.startAnimation(fromBottom)
            binding.fabEditFood.startAnimation(fromBottom)
            binding.fabAddModifier.startAnimation(fromBottom)
            binding.fabEditModifier.startAnimation(fromBottom)
        }
        else{
            binding.fab.startAnimation(rotateClose)
            binding.fabEditModifier.startAnimation(toBottom)
            binding.fabAddModifier.startAnimation(toBottom)
            binding.fabEditFood.startAnimation(toBottom)
            binding.fabAddFood.startAnimation(toBottom)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked){
            binding.fabAddFood.visibility = View.VISIBLE
            binding.fabAddModifier.visibility = View.VISIBLE
            binding.fabEditModifier.visibility = View.VISIBLE
            binding.fabEditFood.visibility = View.VISIBLE
        }
        else{
            binding.fabAddFood.visibility = View.INVISIBLE
            binding.fabAddModifier.visibility = View.INVISIBLE
            binding.fabEditModifier.visibility = View.INVISIBLE
            binding.fabEditFood.visibility = View.INVISIBLE
        }
    }

    private fun setClickable(clicked : Boolean){
        if (!clicked){
            binding.fabAddFood.isClickable = true
            binding.fabAddModifier.isClickable = true
            binding.fabEditModifier.isClickable = true
            binding.fabEditFood.isClickable = true
        }
        else{
            binding.fabAddFood.isClickable = false
            binding.fabAddModifier.isClickable = false
            binding.fabEditModifier.isClickable = false
            binding.fabEditFood.isClickable = false
        }
    }

    private fun showDialog() {
        val fragmentManager = supportFragmentManager;
        val newFragment = AddNewItemDialogFragment();

//        if (mIsLargeLayout) {
//            // The device is using a large layout, so show the fragment as a dialog
//            newFragment.show(fragmentManager, "dialog");
//        } else {
            // The device is smaller, so show the fragment fullscreen
            val transaction = fragmentManager.beginTransaction ()
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.menu_creator_nav_host)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}