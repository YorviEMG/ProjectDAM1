package com.example.appproject.services

import com.example.appproject.entidad.Juego
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServicesJuego {
    //endpoint para listar medicamentos
    @GET("/api/Juegos")
    fun findAll(): Call<List<Juego>>
    @POST("/api/Juegos")
    fun save(@Body med:Juego): Call<Juego>
    @GET("/api/Juegos/{codigo}")
    fun findById(@Path("codigo") cod:Int): Call<Juego>
    @PUT("/api/Juegos")
    fun update(@Body med:Juego): Call<Juego>
    @DELETE("/api/Juegos/{codigo}")
    fun deleteById(@Path("codigo") cod:Int): Call<Void>
}