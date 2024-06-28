package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

private lateinit var txtUsuario: TextInputEditText
private lateinit var txtCorreo: TextInputEditText
private lateinit var txtClave: TextInputEditText
private lateinit var txtClave2: TextInputEditText
private lateinit var btnRegistro: Button
private lateinit var btnVolver: Button
class RegistroActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtUsuario  = findViewById(R.id.txtLoginReg)
        txtCorreo   = findViewById(R.id.txtCorreoReg)
        txtClave    = findViewById(R.id.txtClaveReg)
        txtClave2   = findViewById(R.id.txtClaveReg2)
        btnRegistro = findViewById(R.id.btnRegistroReg)
        btnVolver   = findViewById(R.id.btnVolverReg)
        btnRegistro.setOnClickListener{ registro()}
        btnVolver.setOnClickListener{ goLogin() }
    }
    fun registro(){

    }
    fun goLogin(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}