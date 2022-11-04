package com.example.myfyptest.menuCreator.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true)
class Modifier(var name : String ,
               private var _isMultipleChoice : Boolean,
               private var _isRequired : Boolean) : MenuInterface {

    private var _modifierList = mutableListOf<String>()

    @Transient
    private lateinit var menu : Menu

    @Transient
    private var _selected = MutableLiveData<MutableList<String>>(mutableListOf())
    val selected : List<String>?
        get() = _selected.value
    private var _totalPrice = MutableLiveData<Double>(0.0)
    val price : LiveData<Double>
        get() = _totalPrice
    val isMultipleChoice : Boolean
        get() = _isMultipleChoice
    val isRequired : Boolean
        get() = _isRequired

    fun addItem(itemId: String) : Modifier {
        _modifierList.add(itemId)
        return this
    }

    fun build(): Modifier {
        return this
    }

//    fun selectItem(item : String){
//        if (_modifierList.contains(item)) {
//            _selected.value?.add(item)
//            updatePrice()
//        } else
//            throw Exception("Item not contained inside modifier list!")
//    }
//
//    fun deselectItem(item: String){
//        if (!_selected.value!!.remove(item))
//            throw Exception("Item is not available in selected list!")
//        updatePrice()
//    }
//
//    fun getSelection():List<String>?{
//        checkRequired()
//        return selected
//    }

    fun isItemInModifier(itemId : String) : Boolean{
        return _modifierList.contains(itemId)
    }

    private fun updatePrice(){
        var sum = 0.0
        for( i in selected!!)
            sum += menu.getModifierItem(i)?.price!!
        _totalPrice.value = sum
    }

    private fun checkRequired(){
        if (isRequired && _selected.value.isNullOrEmpty())
            throw Exception("Value must be selected")
    }

    override fun setMenuDatabase() {
        menu = Menu
    }

}