package com.example.appproject.service

import com.example.appproject.entidad.Categoria
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceCategoria {
    @GET("/api/Categoria")
    fun findAll(): Call<List<Categoria>>
}