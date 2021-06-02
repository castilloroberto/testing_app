package com.example.testingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var listView1: ListView
    private lateinit var refillsJSON: JSONObject
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView1 = findViewById(R.id.list_view1)
        refillsJSON = Refills.getRefillsFromFile("refills.json", this)
        fillList(refillsJSON,"super")

    }


     private fun fillList(json:JSONObject,objName:String){
        val wholeList = getArrays(json,objName)
        val stringList = arrayOfNulls<String>(wholeList.size)
        for (i in 0 until wholeList.size){
            val refill = wholeList[i]
            stringList[i] = refill.title
        }
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,stringList)
        listView1.adapter = adapter
    }

     private fun getArrays(json:JSONObject,objName:String):ArrayList<Refills>{
        val refillsList = ArrayList<Refills>()

        try {
            val refills = json.getJSONArray(objName)
            (0 until refills.length()).mapTo(refillsList){
                Refills(
                        refills.getJSONObject(it).getString("cod"),
                        refills.getJSONObject(it).getString("description"),
                        refills.getJSONObject(it).getString("duration"),
                        refills.getJSONObject(it).getInt("price"),
                        refills.getJSONObject(it).getString("title")

                )
            }
        }catch (e:JSONException){
            e.printStackTrace()
        }

        return refillsList
    }

    fun onRadioClicked(view: View){
        if (view is RadioButton){
            val checked = view.isChecked

            when(view.getId()){
                R.id.radio_super ->
                    if (checked){
                        fillList(refillsJSON,"super")

                    }
                R.id.radio_calls ->
                    if (checked){
                        fillList(refillsJSON,"calls")

                    }
                R.id.radio_social ->
                    if (checked){
                        fillList(refillsJSON,"social")

                    }
            }
        }

    }
    fun onFABClick(view: View){
        val i = Intent(this,scrollView::class.java)
        startActivity(i)
    }
    fun clearEdit(view: View){
        et_num.setText("")
    }
}