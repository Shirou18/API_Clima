package com.dev.clima

data class WeatherResponsive(
    val main: Main,
    val weather:List<Weather>,
    val name:String
)

data class Main(
    val temp:Float,
    val humidity:Float
)

data class Weather(
    val description:String
)