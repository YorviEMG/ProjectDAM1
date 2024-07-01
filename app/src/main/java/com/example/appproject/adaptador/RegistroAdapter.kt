package com.example.appproject.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.JugadorActualizarActivity
import com.example.appproject.entidad.Jugador
import com.example.appproject.R
import com.example.appproject.entidad.Registro
import com.example.appproject.utils.AppConfig

class RegistroAdapter(var lista:List<Registro>): RecyclerView.Adapter<ViewRegistro>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewRegistro {
        var vista= LayoutInflater.from(parent.context).
        inflate(R.layout.item_registro,parent,false)
        return ViewRegistro(vista)
    }
    override fun getItemCount(): Int {
        return lista.size
    }
    override fun onBindViewHolder(holder: ViewRegistro, position: Int) {
       //holder.tvId.setText(lista.get(position).id.toString())
       //holder.tvTorneo.setText(lista.get(position).torneoId.toString())
       //holder.tvJugador.setText(lista.get(position).jugadorId.toString())
       //holder.tvJuego.setText(lista.get(position).juegoId.toString())
       //holder.tvEstado.setText(lista.get(position).estadoId.toString())
        //obtener contexto
        //var CONTEXTO=holder.itemView.context
        holder.tvId.text = lista[position].id.toString()
        holder.tvTorneo.text = lista[position].nombreTorneo
        holder.tvJugador.text = lista[position].nombreJugador
        holder.tvJuego.text = lista[position].nombreJuego
        holder.tvEstado.text = lista[position].nombreEstado
    }
}