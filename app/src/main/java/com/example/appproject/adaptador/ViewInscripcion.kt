package com.example.appproject.adaptador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.R

class ViewInscripcion(item:View):RecyclerView.ViewHolder(item) {
    //
    var tvID:TextView
    var tvNombre:TextView
    //
    init {
        tvID=item.findViewById(R.id.tvID)
        tvNombre=item.findViewById(R.id.tvNombreIns)
    }
}