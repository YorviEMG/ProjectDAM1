package com.example.appproject.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R

class ViewJuego(item:View): RecyclerView.ViewHolder(item) {
    //declarar atributos
    var tvCodigo:TextView
    var tvNombre:TextView
    var tvPlataforma:TextView
    var tvDesarrollador:TextView
    var tvNombreCategoria:TextView



    /*bloque init (referenciar)*/
    init{
        tvCodigo=item.findViewById(R.id.tvCodigoJuego)
        tvNombre=item.findViewById(R.id.tvNombreJuego)
        tvPlataforma=item.findViewById(R.id.tvPlataformaJuego)
        tvDesarrollador=item.findViewById(R.id.tvDesarrolladorJuego)
        tvNombreCategoria=item.findViewById(R.id.tvNombreCategoriaJuego)

    }
}