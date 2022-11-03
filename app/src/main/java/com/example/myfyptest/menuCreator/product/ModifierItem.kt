package com.example.myfyptest.menuCreator.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ModifierItem(name:String,price:Double){
    private val _name :String = name
    val name : String
        get() = _name

    private var _price : Double = price
    val price : Double
        get() = _price
}