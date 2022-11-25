package com.example.myfyptest.menuCreator.product

//Menu is the product database
object ProductDatabase {
    private val productDatabase : HashMap<String,in Product> = hashMapOf()
    private val modifierDatabase = hashMapOf<String,Modifier>()
    private val modifierItemDatabase = hashMapOf<String,ModifierItem>()

    fun getFoodDataset() : HashMap<String, in Product> {
        return productDatabase
    }

    fun getModifierDataset() : HashMap<String, Modifier> {
        return modifierDatabase
    }

    fun insertMenu(product : Food) : String{
        val id = product.productTag.tagIdHeader + "-" + productDatabase.size
        productDatabase[id] = product
        return id
    }

    fun insertModifier(modifier : Modifier) : String{
        val id = "M-" + modifierDatabase.size
        modifierDatabase[id] = modifier
        return id
    }

    fun insertModifierItem(item : ModifierItem) : String{
        val id = "MI-" + modifierItemDatabase.size
        modifierItemDatabase[id] = item
        return id
    }

    fun getProduct(keyId : String) : Any? {
        return productDatabase[keyId]
    }

    fun getModifier(keyId : String) : Modifier?{
        return modifierDatabase[keyId]
    }

    fun getModifierList() : List<String>{
        return modifierDatabase.keys.toList()
    }

    fun getModifierItem(keyId : String) : ModifierItem?{
        return modifierItemDatabase[keyId]
    }
}