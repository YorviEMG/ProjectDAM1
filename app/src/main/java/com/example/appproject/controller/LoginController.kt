package com.example.appproject.controller

import android.content.ContentValues
import com.example.appproject.entidad.Usuario
import com.example.appproject.utils.AppConfig

class LoginController {
    //registrarUsuario correo
    fun save(bean:Usuario):Int{
        var estado = -1
        //acceso a la BD en modo escritura
        var cn = AppConfig.BD.writableDatabase
        //crear un objet d la clase contentValues
        var content = ContentValues()
        //asignar claves "nombres de los campos de la tabla tb_usuario"
        content.put("nom", bean.nombre)
        content.put("correo", bean.correo)
        content.put("log", bean.log)
        content.put("rol", bean.rol)

        estado = cn.insert("tb_usuario", "cod", content).toInt()

        return estado
    }
    fun findUsuario(correo:String):Usuario?{
        var bean:Usuario? = null
        var CN=AppConfig.BD.readableDatabase
        var SQL="select * from tb_usuario where correo=? LIMIT 1"
        var RS=CN.rawQuery(SQL, arrayOf(correo))
        if(RS.moveToNext()){
            bean=Usuario(RS.getInt(0),RS.getString(1),
                RS.getString(2),RS.getString(3),
                RS.getString(4), RS.getString(5))
        }
        RS.close()
        CN.close()
        return bean
    }
    fun findUsuarioByMail(correo:String):Int{
        var estado:Int = -1
        var CN=AppConfig.BD.readableDatabase
        var SQL="select cod from tb_usuario where correo=? LIMIT 1"
        var RS=CN.rawQuery(SQL, arrayOf(correo))
        if(RS.moveToNext()){
            estado=RS.getInt(0)
        }
        RS.close()
        CN.close()
        return estado
    }
    fun findUsuarioByUser(user:String):Int{
        var estado:Int = -1
        var CN=AppConfig.BD.readableDatabase
        var SQL="select cod from tb_usuario where nom=? LIMIT 1"
        var RS=CN.rawQuery(SQL, arrayOf(user))
        if(RS.moveToNext()){
            estado=RS.getInt(0)
        }
        RS.close()
        CN.close()
        return estado
    }
}