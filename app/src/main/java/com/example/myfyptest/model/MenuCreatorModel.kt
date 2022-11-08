package com.example.myfyptest.model

import androidx.lifecycle.ViewModel
import com.example.myfyptest.menuCreator.product.Food
import com.example.myfyptest.menuCreator.product.ProductDatabase
import com.example.myfyptest.menuCreator.product.ProductTag

class MenuCreatorModel : ViewModel() {
    private val menu = ProductDatabase

    private lateinit var currentFood: Food

    fun selectFood(foodId : String){
        currentFood = menu.getProduct(foodId) as Food
    }

    fun addFood(name: String, price: Double, tag: ProductTag, isModifiable: Boolean) : String{
        lateinit var msg : String
        try {
            val newFood = Food(name,price,tag,isModifiable)
            msg = menu.insertMenu(newFood)
        } catch (e: Exception){
            return "Exception : ${e.message}"
        }
        return msg
    }

    fun deleteFood(foodId: String){

    }

    fun selectModifier(modifierId : String){

    }

}