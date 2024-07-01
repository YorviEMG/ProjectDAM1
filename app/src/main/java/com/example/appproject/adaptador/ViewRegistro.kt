package com.example.appproject.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R

class ViewRegistro(item: View):RecyclerView.ViewHolder(item) {
    //
    var tvId:TextView
    var tvTorneo:TextView
    var tvJugador:TextView


    init {
        tvId=item.findViewById(R.id.tvIDReg)
        tvTorneo=item.findViewById(R.id.tvTorneo)
        tvJugador=item.findViewById(R.id.tvJugador)
    }
}