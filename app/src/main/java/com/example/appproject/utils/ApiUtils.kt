package com.example.appproject.utils


import com.example.appproject.services.ApiServicesJuego
import com.example.appproject.services.ApiServicesJugador

class ApiUtils {
    companion object {
        const val BASE_URL = "http://www.cursomovil.somee.com"

        fun getAPIServiceJugador(): ApiServicesJugador {
            return RetrofitClient.getClient(BASE_URL).create(ApiServicesJugador::class.java)
        }

        fun getAPIServiceJuego(): ApiServicesJuego {
            return RetrofitClient.getClient(BASE_URL).create(ApiServicesJuego::class.java)
        }
    }
}

import com.example.appproject.service.ApiServiceInscripcion


class ApiUtils {

    companion object {
       const val BASE_URL="http://www.cursomovil.somee.com/"
            fun getAPIServiceInscripcion(): ApiServiceInscripcion {
              return RetrofitClient.getClient(BASE_URL).create(ApiServiceInscripcion::class.java)
       }
    }
}

