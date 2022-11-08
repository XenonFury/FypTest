package com.example.myfyptest.menuCreator.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Order (orderId : String) : MenuInterface {
    private lateinit var menu : ProductDatabase
    private val _orderList : HashMap<FoodObject,Int> =  hashMapOf()
    val orderList : HashMap<FoodObject,Int>
        get() = _orderList

    @Transient
    private var _foodSubtotal = MutableLiveData<Double>(0.0)
    val foodSubtotal : LiveData<Double>
        get() {
            calculateFoodSubtotal()
            return _foodSubtotal
        }

    override fun setMenuDatabase() {
        menu = ProductDatabase
    }

    fun addToOrder(product : FoodObject,quantity : Int){
        if (quantity > 0)
            orderList[product] = quantity
    }

    fun selectProductModifierItem(product: FoodObject, modifierId: String, modifierItemId: String) : Boolean{
        return if (!_orderList.containsKey(product) && !product.food.isModifierItemInFood(modifierItemId,modifierId))
            false
        else{
            product.addItem(modifierItemId,modifierId)
            true
        }
    }

    fun deselectProductModifierItem(product: FoodObject,modifierItemId: String) : Boolean{
        return product.removeItem(modifierItemId)
    }

    fun calculateFoodSubtotal(){
        var sum = 0.0
        for (item in _orderList.keys){
            sum += _orderList[item]?.times(item.totalPrice.value!!) ?: 0.0
        }
        _foodSubtotal.value = sum
    }


}