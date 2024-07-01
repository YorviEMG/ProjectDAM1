package com.example.appproject.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R


class ViewUsuario(item:View):RecyclerView.ViewHolder(item) {
    var tvUsuario: TextView
    var tvCorreo: TextView
    var tvClave: TextView
    var tvLog: TextView
    var tvRol: TextView

    init {
        tvUsuario = item.findViewById(R.id.tvNomUsu)
        tvCorreo = item.findViewById(R.id.tvCorreoUsu)
        tvClave = item.findViewById(R.id.tvClaveUsu)
        tvLog = item.findViewById(R.id.tvLogUsu)
        tvRol = item.findViewById(R.id.tvRolUsu)
    }
}