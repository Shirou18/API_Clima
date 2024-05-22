package com.dev.clima

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.clima.databinding.ActivityMainBinding
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuscar.setOnClickListener {
            val ciudad = binding.editTextCiudad.text.toString()
            if (ciudad.isNotBlank()) {
                obtenerDatosClima(ciudad)
            } else {
                Toast.makeText(this, "Por favor, ingrese el nombre de una ciudad", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun obtenerDatosClima(ciudad: String) {
        val apiKey = "9a6414c64f53b60efd25378b95f5ebc0"
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$ciudad&appid=$apiKey&units=metric"

        Fuel.get(url).response { _, response, result ->
            result.fold(
                success = { data ->
                    val responseBody = data.decodeToString()
                    val weatherResponse = Gson().fromJson(responseBody, WeatherResponsive::class.java)
                    runOnUiThread {
                        binding.textViewResultadoClima.text = """
                            Ciudad: ${weatherResponse.name}
                            Temperatura: ${weatherResponse.main.temp}°C
                            Humedad: ${weatherResponse.main.humidity}%
                            Descripción: ${weatherResponse.weather[0].description}
                        """.trimIndent()
                    }
                },
                failure = { error ->
                    runOnUiThread {
                        Toast.makeText(this, "Error fetching weather data: ${error.message}", Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
    }
}