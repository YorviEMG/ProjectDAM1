package com.example.appproject.entidad

class Usuario{
    var cod:Int=0
    var nombre:String=""
    var correo:String=""
    var clave:String=""
    var log:String=""
    var rol:String=""
    constructor(cod:Int, nombre:String, correo:String, clave:String,
                log:String, rol:String){
        this.cod = cod
        this.nombre = nombre
        this.correo = correo
        this.clave = clave
        this.log = log
        this.rol = rol
    }
    constructor(){

    }

}