package com.example.weatherapp

//  import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.Constant
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.api.WeatherModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String){
        //Log.i("City name: ",city)
        // Loading if has data
        _weatherResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey,city)
                if (response.isSuccessful){
                    // Show the data if successful
                    //Log.i("Response: ",response.body().toString())
                    response.body()?.let{
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
                    // Show the error message
                    //Log.i("Error: ", response.message())
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            }catch (e : Exception){
                // Show the error message for exception
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }




        }

    }
}