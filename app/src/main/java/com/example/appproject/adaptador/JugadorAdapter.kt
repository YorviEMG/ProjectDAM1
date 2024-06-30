package com.example.appproject.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.JugadorActualizarActivity
import com.example.appproject.entidad.Jugador
import com.example.appproject.R
import com.example.appproject.utils.AppConfig

class JugadorAdapter(var lista:List<Jugador>): RecyclerView.Adapter<ViewJugador>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewJugador {
        var vista= LayoutInflater.from(parent.context).
        inflate(R.layout.item_jugador,parent,false)
        return ViewJugador(vista)
    }
    override fun getItemCount(): Int {
        return lista.size
    }
    override fun onBindViewHolder(holder: ViewJugador, position: Int) {
        holder.tvCodigo.setText(lista.get(position).Id.toString())
        holder.tvNombre.setText(lista.get(position).nombre)
        holder.tvApellido.setText(lista.get(position).apellido)
        holder.tvEdad.setText(lista.get(position).edad)
        holder.tvNacionalidad.setText(lista.get(position).nacionalidad)
        //obtener contexto
        var CONTEXTO=holder.itemView.context
        holder.itemView.setOnClickListener{
            var intent=Intent(AppConfig.CONTEXT,JugadorActualizarActivity::class.java)
            //asignar clave
            intent.putExtra("codigo",lista.get(position).Id)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(AppConfig.CONTEXT,intent,null)
        }
    }
}