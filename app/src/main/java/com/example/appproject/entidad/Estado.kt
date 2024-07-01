package com.example.appproject.entidad

class Estado(var id:Int, var nombre:String) {
    override fun toString(): String {
        return nombre // Esto es lo que se mostrará en el Spinner
    }
}