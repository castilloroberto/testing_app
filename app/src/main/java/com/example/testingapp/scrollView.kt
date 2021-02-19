package com.example.testingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_scroll_view.*
import org.json.JSONException
import org.json.JSONObject

class scrollView : AppCompatActivity() {

    private lateinit var listView2:ListView
    private lateinit var jsonCountries: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view)
        listView2 = list_view2
        jsonCountries = Country.getRefillsFromFile("countries.json",this)
        Log.i("onCreate: ", jsonCountries.toString())
        fillList(jsonCountries,"countries")
    }

    private fun fillList(json:JSONObject,objName:String){
        val wholeList = getArrays(json,objName)
        val stringList = arrayOfNulls<String>(wholeList.size)
        for (i in 0 until wholeList.size){
            val refill = wholeList[i]
            stringList[i] = refill.name
        }
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,stringList)
        listView2.adapter = adapter
    }
    fun getArrays(json: JSONObject, objName:String):ArrayList<Country>{
        val refillsList = ArrayList<Country>()

        try {
            val refills = json.getJSONArray(objName)
            (0 until refills.length()).mapTo(refillsList){
                Country(
                    refills.getJSONObject(it).getString("name"),
                    refills.getJSONObject(it).getString("code")
                )
            }
        }catch (e: JSONException){
            e.printStackTrace()
        }

        return refillsList
    }

    fun clearEditText(view: View){
        et_num2.setText("")
    }

}