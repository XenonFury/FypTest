package com.example.myfyptest.menuCreator.product

import android.util.Log

const val TAG = "Food Class"

class Food(name: String,
           price: Double,
           productTag : ProductTag,
           isModifiable : Boolean) : Product("Food"),MenuInterface{

    override var _name: String = name
        set(name) {
            if (name.isNotBlank()) field = name
            else throw java.lang.Exception("Name must not be null")
        }
    val name : String
        get() = _name

    override var _price: Double = price
        set(price) {
            if (!price.isNaN()) field = price
            else throw java.lang.Exception("Price must not be null")
        }
    val price : Double
        get() = _price

    override var _productTag = productTag
    val productTag : ProductTag
        get() = _productTag

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
    private lateinit var menu : ProductDatabase

    override fun setMenuDatabase() {
        menu = ProductDatabase
    }

    init {
        setMenuDatabase()
        if (_isModifiable)
            _modifierList = mutableListOf<String>()
    }

    fun addModifier(modifierId : String){
        _modifierList!!.add(modifierId)
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