package com.example.myfyptest.menuCreator.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

const val TAG = "Food Class"

class Food(foodId : String,
           name: String,
           price: Double,
           foodTag : ProductTagEnum,
           isModifiable : Boolean) : Product("Food"),MenuInterface{

    override var _name: String = name
        set(name) {
            if (name.isNotBlank()) field = name
            else throw java.lang.Exception("Name must not be null")
        }
    override var _productId: String = foodId
    val productId : String
        get() = _productId

    val name : String
        get() = _name

    override var _price: Double = price
        set(price) {
            if (!price.isNaN()) field = price
            else throw java.lang.Exception("Price must not be null")
        }
    val price : Double
        get() = _price

    override var _foodTag = foodTag
    val foodTag : ProductTagEnum
        get() = _foodTag

    private var _allTimeSales: Int = 0
    val allTimeSales : Int
        get() = _allTimeSales
    private var _isModifiable:Boolean = isModifiable
    val isModifiable : Boolean
        get() = _isModifiable
    private var _modifierList : MutableList<String>? = null
    val modifierList : List<String>?
        get() = _modifierList

    @Transient
    private lateinit var menu : Menu

    override fun setMenuDatabase() {
        menu = Menu
    }

    init {
        setMenuDatabase()
        if (_isModifiable)
            _modifierList = mutableListOf<String>()
    }

    fun addModifier(modifier : Modifier){
        _modifierList!!.add(menu.insertModifier(modifier))
    }

    fun removeModifier(modifierId: String){
        if (_modifierList==null)
            throw Exception("Modifier list is not initialized")
        else
            if (_modifierList!!.remove(modifierId))
                Log.d(TAG,"Modifier removed")
            else
                Log.d(TAG,"Modifier not found!")
    }

    fun isModifierItemInFood(itemId: String,modifierId: String) : Boolean{
        return menu.getModifier(modifierId)?.isItemInModifier(itemId)!! && isModifierInFood(modifierId)
    }

    fun isModifierInFood(modifierId: String) : Boolean{
        return _isModifiable && _modifierList?.contains(modifierId)!!
    }

}