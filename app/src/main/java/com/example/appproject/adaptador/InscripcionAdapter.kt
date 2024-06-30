package com.example.appproject.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R
import com.example.appproject.entidad.Inscripcion
class InscripcionAdapter(var lista:List<Inscripcion>): RecyclerView.Adapter<ViewInscripcion>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewInscripcion {
        //
        var vista=LayoutInflater.from(parent.context).inflate(R.layout.item_inscripcion,parent,false)
        return ViewInscripcion(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewInscripcion, position: Int) {
        //
        holder.tvID.text = (lista.get(position).id.toString())
        holder.tvNombre.text = (lista.get(position).nombre)
        //obtener contexto
        var CONTEXTO=holder.itemView.context
    }

}