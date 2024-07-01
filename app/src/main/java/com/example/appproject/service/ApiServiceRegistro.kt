package com.example.appproject.service

import com.example.appproject.entidad.Juego
import com.example.appproject.entidad.Registro
import retrofit2.Call
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.POST


interface ApiServiceRegistro {
    //
    @GET("/api/Registros")
    fun findAll(): Call<List<Registro>>
    @POST("/api/Registros")
    fun save(@Body re: Registro): Call<String>

}