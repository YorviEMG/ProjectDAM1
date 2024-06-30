package com.example.appproject.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.JuegoActualizarActivity
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
        val juego = lista[position]
        holder.tvCodigo.text = juego.Id.toString()
        holder.tvNombre.text = juego.nombre ?: "N/A"
        holder.tvPlataforma.text = juego.plataforma ?: "N/A"
        holder.tvDesarrollador.text = juego.desarrollador ?: "N/A"
        holder.tvIdCategoria.text = juego.IdCategoria.toString() // Convertir IdCategoria a String

        // Obtener contexto
        val contexto = holder.itemView.context
        holder.itemView.setOnClickListener {
            val intent = Intent(contexto, JuegoActualizarActivity::class.java)
            // Asignar clave
            intent.putExtra("codigo", juego.Id)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(contexto, intent, null)
        }
    }
}
