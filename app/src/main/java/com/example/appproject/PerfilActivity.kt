package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private lateinit var txtUsu:TextView
private lateinit var txtCorreo:TextView
private lateinit var txtLog:TextView
private lateinit var txtRol:TextView
private lateinit var btnHome  : ImageButton
private lateinit var btnPerfil: ImageButton
private lateinit var btnLogout: ImageButton
class PerfilActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.perfil_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtUsu = findViewById(R.id.tvUsuarioProf)
        txtCorreo = findViewById(R.id.tvCorreoProf)
        txtLog = findViewById(R.id.tvLogProf)
        txtRol = findViewById(R.id.tvRolProf)
        btnHome = findViewById(R.id.btnHomeProf)
        btnPerfil = findViewById(R.id.btnPerfilProf)
        btnLogout = findViewById(R.id.btnLogoutProf)
        btnHome.setOnClickListener{ goHome() }
        btnPerfil.setOnClickListener{ goPerfil() }
        btnLogout.setOnClickListener{ logout() }
        initial()
    }
    fun initial(){
        var info = intent.extras
        if (info != null && info.containsKey("name")) {
            var usuName = info.getString("name").toString()
            var correo  = info.getString("correo").toString()
            var log     = info.getString("log").toString()
            var rol     = info.getString("rol").toString()
            txtUsu.setText(usuName)
            txtCorreo.setText(correo)
            txtLog.setText(log)
            txtRol.setText(rol)
        }

    }
    fun goHome(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun goPerfil(){
        var intent = Intent(this, PerfilActivity::class.java)
        startActivity(intent)
    }
    fun logout(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}