package com.example.myfyptest.adapter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myfyptest.menuCreator.product.ModifierItem
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ModifierJsonAdapter {
    @ToJson
    fun toJson(selected : MutableLiveData<MutableList<ModifierItem>>) : List<ModifierItem>{
        return selected.value!!
    }

    @FromJson
    fun fromJson(string : List<ModifierItem>) : MutableLiveData<MutableList<ModifierItem>>{
        Log.d("MainActivity1",string[1].name)
        return MutableLiveData<MutableList<ModifierItem>>(string.toMutableList())
    }

    @ToJson
    fun toJson1(prc : MutableLiveData<Double>) : String{
        return prc.value.toString()
    }

    @FromJson
    fun fromJson1(string : String) : MutableLiveData<Double>{
        return MutableLiveData(string.toDouble())
    }

}