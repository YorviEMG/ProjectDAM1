package com.example.appproject.adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.appproject.entidad.Juego

class JuegoComboAdapter(context: Context, private var items: List<Juego>) : ArrayAdapter<Juego>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_item)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_dropdown_item)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup, resource: Int): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.nombre
        return view
    }
}