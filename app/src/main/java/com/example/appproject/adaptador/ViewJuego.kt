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
    var tvCategoria:TextView
    var tvDesarrollador:TextView
    var tvIdCategoria:TextView



    /*bloque init (referenciar)*/
    init{
        tvCodigo=item.findViewById(R.id.tvCodigoJuego)
        tvNombre=item.findViewById(R.id.tvNombreJuego)
        tvPlataforma=item.findViewById(R.id.tvPlataformaJuego)
        tvCategoria=item.findViewById(R.id.tvCategoriaJuego)
        tvDesarrollador=item.findViewById(R.id.tvDesarrolladorJuego)
        tvIdCategoria=item.findViewById(R.id.tvIdCategoriaJuego)

    }
}