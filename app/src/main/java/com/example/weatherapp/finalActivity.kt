package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.databinding.ActivityFinalBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class finalActivity : AppCompatActivity() {
    //4f95a1ab02491334fdeeb8932c1ddaf8
//    https://api.openweathermap.org/data/2.5/
    lateinit var binding: ActivityFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_final)

        val sView = binding.SearchView
        val CityName = binding.CityName

        //Retrofit Intance
        sView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                dislplay(query!!)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })

    }

    private fun dislplay( CityName : String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(ApiInterface::class.java)
        val res = retrofit.getweatherdata(CityName , "4f95a1ab02491334fdeeb8932c1ddaf8" , "metric")
        res.enqueue(object : Callback<weatherData>{
            override fun onResponse(call: Call<weatherData>, response: Response<weatherData>) {
                val res_body = response.body()
                if(response.isSuccessful){

                    if (res_body != null) {
                        binding.CityName.text = "  "+res_body.weather.firstOrNull()?.description.toString()+"  "
                        binding.textView3.text = res_body.main.temp.toString()+"°"+"c"
                        binding.mintemp.text = res_body.main.tempMin.toString()+"°"+"c"
                        binding.maxtemp.text = res_body.main.tempMax.toString()+"°"+"c"
                        binding.windSpeed.text = res_body.wind.speed.toString()
                        binding.meter.text = "m/sec"
                        binding.Humidity.text = res_body.main.humidity.toString()+"%"
                        binding.pressure.text = res_body.main.pressure.toString()
                        binding.hPa.text = "hPa"
                        binding.visibility.text = res_body.visibility.toString()
                        binding.city.text = "  "+CityName+"  "
                    }

                }else{
                    Toast.makeText(this@finalActivity , "Enter a Correct Cityname", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<weatherData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}