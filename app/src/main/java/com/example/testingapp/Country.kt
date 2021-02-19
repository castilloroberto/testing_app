package com.example.testingapp

import android.content.Context
import org.json.JSONException
import org.json.JSONObject

class Country(
    val name:String,
    val code:String
) {

    companion object{
        fun getRefillsFromFile(fileName:String,context: Context): JSONObject{
            lateinit var refillsJSON: JSONObject

            try {

                val jsonString = loadJsonFromAssets(fileName,context)
                val json = JSONObject(jsonString)
                refillsJSON = json

            }catch (e:JSONException){
                e.printStackTrace()
            }

            return refillsJSON
        }

        fun loadJsonFromAssets(fileName: String,context: Context): String?{

            var json: String? = null
            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charsets.UTF_8)

            }catch (ex:java.io.IOException){
                ex.printStackTrace()
                return null
            }
            return json
        }
    }

}