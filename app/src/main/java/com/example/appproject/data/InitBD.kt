package com.example.appproject.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appproject.utils.AppConfig

class InitBD:SQLiteOpenHelper(AppConfig.CONTEXT, AppConfig.BD_NAME, null, AppConfig.VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table tb_usuario(" +
                "cod integer primary key autoincrement," +
                "nom varchar(30)," +
                "correo varchar(50)," +
                "clave varchar(30)," +
                "log varchar(30)," +
                "rol varchar(30))")

        db.execSQL("insert into tb_usuario values(null,'Felipe Melo', 'felipemelo@hotmail.com', '123456789', 'CORREO', 'ADMIN')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}