package com.example.myfyptest.menuCreator.product

//Menu is the product database
object Menu {
    private val productDatabase : HashMap<String,in Product> = hashMapOf()
    private val modifierDatabase = hashMapOf<String,Modifier>()
    private val modifierItemDatabase = hashMapOf<String,ModifierItem>()


    fun insertMenu(product : Food){
        val id  =
        productDatabase
        productDatabase["A-01"] = product
    }

    fun insertModifier(modifier : Modifier) : String{
        val id = "M-@{modifierDatabase.size}"
        modifierDatabase[id] = modifier
        return id
    }

    fun insertModifierItem(item : ModifierItem) : String{
        val id = "MI-@{modifierItemDatabase.size}"
        modifierItemDatabase[id] = item
        return id
    }

    fun getProduct(keyId : String) : Any?{
        return productDatabase[keyId]
    }

    fun getModifier(keyId : String) : Modifier?{
        return modifierDatabase[keyId]
    }

    fun getModifierItem(keyId : String) : ModifierItem?{
        return modifierItemDatabase[keyId]
    }
}