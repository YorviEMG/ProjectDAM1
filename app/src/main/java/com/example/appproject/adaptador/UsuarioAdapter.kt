package com.example.appproject.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R
import com.example.appproject.entidad.Usuario

class UsuarioAdapter(var lista: List<Usuario>):RecyclerView.Adapter<ViewUsuario>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewUsuario {
        var vista= LayoutInflater.from(parent.context).
        inflate(R.layout.item_usuario_log,parent,false)
        return ViewUsuario(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewUsuario, position: Int) {
        holder.tvUsuario.setText(lista.get(position).nombre.toString())
        holder.tvCorreo.setText(lista.get(position).correo.toString())
        holder.tvClave.setText(lista.get(position).clave.toString())
        holder.tvLog.setText(lista.get(position).log.toString())
        holder.tvRol.setText(lista.get(position).rol.toString())
        var CONTEXTO=holder.itemView.context
        /*holder.itemView.setOnClickListener{
            var intent= Intent(AppConfig.CONTEXT, JugadorActualizarActivity::class.java)
            //asignar clave
            intent.putExtra("codigo",lista.get(position).Id)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(AppConfig.CONTEXT,intent,null)
        }*/
    }
}