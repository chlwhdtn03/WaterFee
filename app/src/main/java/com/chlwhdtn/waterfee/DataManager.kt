package com.chlwhdtn.waterfee

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataManager(context: Context) {
    private val prefs = context.getSharedPreferences("WaterData", Activity.MODE_PRIVATE)

    val itemType = object : TypeToken<ArrayList<House>>() {}.type

    var price: Int
        get() = prefs.getInt("TotalPrice", 0)
        set(value) = prefs.edit().putInt("TotalPrice", value).apply()

    var list: ArrayList<House>
        get() = Gson().fromJson(prefs.getString("HouseData", "[]"), itemType)
        set(value) = prefs.edit().putString("HouseData", Gson().toJson(value)).apply()
}