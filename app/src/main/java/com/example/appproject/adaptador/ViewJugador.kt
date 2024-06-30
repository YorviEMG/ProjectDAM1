package com.example.appproject.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R

class ViewJugador(item:View): RecyclerView.ViewHolder(item) {
    //declarar atributos
    var tvCodigo:TextView
    var tvNombre:TextView
    var tvApellido:TextView
    var tvEdad:TextView
    var tvNacionalidad:TextView


    /*bloque init (referenciar)*/
    init{
        tvCodigo=item.findViewById(R.id.tvCodigoJugador)
        tvNombre=item.findViewById(R.id.tvNombreJugador)
        tvApellido=item.findViewById(R.id.tvApellidoJugador)
        tvEdad=item.findViewById(R.id.tvEdadJugador)
        tvNacionalidad=item.findViewById(R.id.tvNacionalidadJugador)

    }
}