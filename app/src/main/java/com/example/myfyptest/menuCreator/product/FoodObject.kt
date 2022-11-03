package com.example.myfyptest.menuCreator.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FoodObject (private val foodId : String) : MenuInterface{

    @Transient
    private lateinit var menu : Menu

    private lateinit var _modifierItemList : MutableList<String>

    @Transient
    private var _food : Food
    val food: Food
        get() = _food

    @Transient
    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice : LiveData<Double>
        get(){
            updatePrice()
            return _totalPrice
        }

    init{
        setMenuDatabase()
        _food = menu.getProduct(foodId) as Food
        if (_food.isModifiable)
            _modifierItemList = mutableListOf()
    }

    override fun setMenuDatabase() {
        menu = Menu
    }

    fun addItem(itemId : String, modifierId: String) : Boolean{
        return if (_food.isModifierItemInFood(itemId,modifierId)){
            _modifierItemList.add(itemId)
            true }
        else
            false
    }

    fun removeItem(itemId: String) : Boolean{
        return _modifierItemList.remove(itemId)
    }

    private fun updatePrice(){
        var sum = _food.price
        if (_food.isModifiable){
            for (i in _modifierItemList)
                sum += menu.getModifierItem(i)?.price!!
        }
        _totalPrice.value = sum
    }

}