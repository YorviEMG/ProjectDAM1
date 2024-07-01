package com.example.appproject.utils


import com.example.appproject.service.ApiServiceInscripcion
import com.example.appproject.service.ApiServiceRegistro
import com.example.appproject.services.ApiServicesJuego
import com.example.appproject.services.ApiServicesJugador

class ApiUtils {
    companion object {
        const val BASE_URL = "http://www.cursomovil.somee.com"

        fun getAPIServiceJugador(): ApiServicesJugador {
            return RetrofitClient2.getClient(BASE_URL).create(ApiServicesJugador::class.java)
        }

        fun getAPIServiceJuego(): ApiServicesJuego {
            return RetrofitClient2.getClient(BASE_URL).create(ApiServicesJuego::class.java)
        }
        fun getAPIServiceInscripcion(): ApiServiceInscripcion {
            return RetrofitClient2.getClient(BASE_URL).create(ApiServiceInscripcion::class.java)
        }

        fun getAPIServiceRegistro(): ApiServiceRegistro {
            return RetrofitClient2.getClient(BASE_URL).create(ApiServiceRegistro::class.java)
        }
    }
}


