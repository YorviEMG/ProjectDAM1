package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private lateinit var btnPerfil: ImageButton
private lateinit var btnLogout: ImageButton
var usuName:String = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnPerfil = findViewById(R.id.btnPerfilMain)
        btnLogout = findViewById(R.id.btnLogoutMain)
        btnPerfil.setOnClickListener{ goPerfil() }
        btnLogout.setOnClickListener{ logout() }
        initial()
    }
    fun initial(){
        var info = intent.extras
        if (info != null && info.containsKey("name")) {
            usuName = info.getString("name").toString()
        }
    }
    fun goPerfil(){
        if (usuName != ""){
            var intent = Intent(this, PerfilActivity::class.java)
            intent.putExtra("name", usuName)
            startActivity(intent)
        }else{
            Toast.makeText(this, "No existe intent",Toast.LENGTH_SHORT)
        }
    }
    fun logout(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}