package com.example.myfyptest.menuCreator.product

abstract class Product (val categoryName:String){
    protected abstract var _productId : String
    protected abstract var _name: String
    protected abstract var _price: Double
    protected abstract var _foodTag : ProductTagEnum
}