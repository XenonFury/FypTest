package com.example.myfyptest.menuCreator.product

class Order : MenuInterface {
    private lateinit var menu : Menu
    private val orderList : HashMap<FoodObject,Int> =  hashMapOf()

    override fun setMenuDatabase() {
        menu = Menu
    }

    fun addToOrder(product : FoodObject,quantity : Int){
        if (quantity > 0)
            orderList[product] = quantity
    }

    fun selectProductModifierItem(product: FoodObject, modifierId: String, modifierItemId: String) : Boolean{
        return if (!orderList.containsKey(product) && !product.food.isModifierItemInFood(modifierItemId,modifierId))
            false
        else{
            product.addItem(modifierItemId,modifierId)
            true
        }
    }

    fun deselectProductModifierItem(product: FoodObject,modifierItemId: String) : Boolean{
        return product.removeItem(modifierItemId)
    }

    fun calculateFoodPrice(productId: String) : Double{
        var sum = 0.0
        for (item in orderList.keys){
            sum += orderList[item]?.times(item.totalPrice.value!!) ?: 0.0
        }
        return sum
    }


}