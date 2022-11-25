package com.example.myfyptest.model

import android.view.Menu
import androidx.lifecycle.ViewModel
import com.example.myfyptest.menuCreator.product.Food
import com.example.myfyptest.menuCreator.product.Modifier
import com.example.myfyptest.menuCreator.product.ProductDatabase
import com.example.myfyptest.menuCreator.product.ProductTag
import java.util.TreeSet
import kotlin.properties.Delegates

class AddMenuViewModel : ViewModel() {
    private lateinit var name : String
    private var price = 0.0
    private var description = ""
    private var productTag = ProductTag.MAIN
    private var isModifiable = false
    private val menu = ProductDatabase

    private val modifierList = TreeSet<String>()


    fun setName(n: String){
        if (n.isNullOrEmpty()) throw IllegalArgumentException("Name is null or empty!")
        name = n
    }

    fun setPrice(p: Double){
        if (p < 0.0) throw IllegalArgumentException("Price must not be negative!")
        price = p
    }

    fun setDescription(desc : String){
        description = desc
    }

    fun setModifiable(boolean: Boolean){
        isModifiable = boolean
    }

    fun addModifier(modifier: String){
        modifierList.add(modifier)
    }

    fun removeModifier(modifier: String){
        modifierList.remove(modifier)
    }

    fun getModifierList() : List<String>{
        return menu.getModifierList()
    }

    fun getModifier(key : String) : Modifier?{
        return menu.getModifier(key)
    }

    fun createFood(){
        val newFood = Food(name,price,productTag,isModifiable)
        newFood.description = description
        if(isModifiable){
            for (i in modifierList)
                newFood.addModifier(i)
        }
        menu.insertMenu(newFood)
    }


}