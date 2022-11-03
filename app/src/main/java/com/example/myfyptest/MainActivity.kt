package com.example.myfyptest

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.myfyptest.databinding.ActivityMainBinding
import com.example.myfyptest.menuCreator.product.*
import com.example.myfyptest.menuCreator.product.adapters.ModifierJsonAdapter
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @OptIn(ExperimentalStdlibApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }
        val database = Firebase.database("https://test-1d074-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef = database.getReference("messages")
        myRef.setValue("Hello, World!")

        val myList : ArrayList<Int> = ArrayList<Int>(100)
        repeat(100){
            myList.add((1..100).random())
        }
        val ref1 = database.getReference().child("1")
        ref1.setValue(myList)

        val wellDone = ModifierItem("Well done",2.0)
        val mediumWell = ModifierItem("Medium Well",1.5)
        val rare = ModifierItem("Rare",1.0)

        val withSides = ModifierItem("Sides",5.0)
        val withoutSides = ModifierItem ("No Sides",0.0)

        val modifier2 = Modifier("Sides", false,true)
            .addItem(withSides)
            .addItem(withoutSides)
            .build()

        val modifier1 = Modifier("Wellness",false,true)
            .addItem(wellDone)
            .addItem(mediumWell)
            .addItem(rare)
            .build()

        val menu = Menu

//        val steak = Food("Steak",15.0,"Main",true)
//        steak.addModifier(modifier1)
//        steak.addModifier(modifier2)
//        modifier1.selectItem(mediumWell)
//        Log.d("MainActivity","Steak price = ${steak.totalPrice.value}")
////        modifier1.selectItem(wellDone)
////        modifier1.deselectItem(mediumWell)
//        Log.d("MainActivity","Steak price = ${steak.totalPrice.value}")

        val moshi : Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(ModifierJsonAdapter())
            .build()
        val jsonAdapter : JsonAdapter<Food> = moshi.adapter<Food>()

//        val json: String = jsonAdapter.toJson(steak)
//        Log.d("MainActivity",json)
//
//        val food2 = jsonAdapter.fromJson(json)
//        println("AAA")

//        val modifierItem2 = jsonAdapter.fromJson(json)
//        Log.d("MainActivity","${modifierItem2?.name} -- ${modifierItem2?.price}")

        // Read from the database
//        myRef.addValueEventListener(object: ValueEventListener() {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                val value = snapshot.getValue<String>()
//                Log.d(TAG, "Value is: " + value)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}