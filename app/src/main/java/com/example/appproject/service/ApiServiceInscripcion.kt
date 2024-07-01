package com.example.appproject.service

import com.example.appproject.entidad.Inscripcion
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceInscripcion {
    @GET("/api/Torneo")
    fun findAll(): Call<List<Inscripcion>>

    @POST("/api/Torneo")
    fun save(@Body ins: Inscripcion): Call<String>

}
