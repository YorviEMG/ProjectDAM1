package com.example.appproject.services

import com.example.appproject.entidad.Jugador
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServicesJugador {
    //endpoint para listar medicamentos
    @GET("/api/Jugador")
    fun findAll(): Call<List<Jugador>>
    @POST("/api/Jugador")
    fun save(@Body med:Jugador): Call<Jugador>
    @GET("/api/Jugador/{codigo}")
    fun findById(@Path("codigo") cod:Int): Call<Jugador>
    @PUT("/api/Jugador")
    fun update(@Body med:Jugador): Call<Jugador>
    @DELETE("/api/Jugador/{codigo}")
    fun deleteById(@Path("codigo") cod:Int): Call<Void>
}