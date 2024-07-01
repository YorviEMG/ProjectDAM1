package com.example.appproject.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.JuegoActualizarActivity
import com.example.appproject.JugadorActualizarActivity
import com.example.appproject.R
import com.example.appproject.entidad.Juego
import com.example.appproject.utils.AppConfig

class JuegoAdapter(var lista:List<Juego>): RecyclerView.Adapter<ViewJuego>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewJuego {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_juego, parent, false)
        return ViewJuego(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewJuego, position: Int) {
        holder.tvCodigo.setText(lista.get(position).id.toString())
        holder.tvNombre.setText(lista.get(position).nombre)
        holder.tvPlataforma.setText(lista.get(position).plataforma)
        holder.tvDesarrollador.setText(lista.get(position).desarrollador)
        holder.tvIdCategoria.setText(lista.get(position).idCategoria.toString())

        //obtener contexto
        var CONTEXTO=holder.itemView.context
        holder.itemView.setOnClickListener{
            var intent=Intent(AppConfig.CONTEXT, JuegoActualizarActivity::class.java)
            //asignar clave
            intent.putExtra("codigo",lista.get(position).id)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(AppConfig.CONTEXT,intent,null)
        }
    }
}
