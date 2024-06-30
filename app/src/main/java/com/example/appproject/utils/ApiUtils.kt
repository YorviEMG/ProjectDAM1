package com.example.appproject.utils

import com.example.appproject.service.ApiServiceInscripcion


class ApiUtils {

    companion object {
       const val BASE_URL="http://www.cursomovil.somee.com/"
            fun getAPIServiceInscripcion(): ApiServiceInscripcion {
              return RetrofitClient.getClient(BASE_URL).create(ApiServiceInscripcion::class.java)
       }
    }
}