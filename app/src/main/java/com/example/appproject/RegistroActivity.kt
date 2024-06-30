package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.controller.LoginController
import com.example.appproject.entidad.Usuario
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

private lateinit var txtUsuario: TextInputEditText
private lateinit var txtCorreo: TextInputEditText
private lateinit var txtClave: TextInputEditText
private lateinit var txtClave2: TextInputEditText
private lateinit var btnRegistro: Button
private lateinit var btnVolver: Button
private lateinit var database: FirebaseDatabase
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
        database = FirebaseDatabase.getInstance("https://project-game-c3986-default-rtdb.firebaseio.com/")

        txtUsuario  = findViewById(R.id.txtLoginReg)
        txtCorreo   = findViewById(R.id.txtCorreoReg)
        txtClave    = findViewById(R.id.txtClaveReg)
        txtClave2   = findViewById(R.id.txtClaveReg2)
        btnRegistro = findViewById(R.id.btnRegistroReg)
        btnVolver   = findViewById(R.id.btnVolverReg)
        btnRegistro.setOnClickListener{ validar()}
        btnVolver.setOnClickListener{ goLogin() }
    }
    fun registro(){
        val usu = txtUsuario.text.toString()
        val cro = txtCorreo.text.toString()
        val cl1 = txtClave.text.toString()
        val cl2 = txtClave2.text.toString()
        var bean=Usuario(0,usu, cro, cl1, CORREO, USER)
        var salida = LoginController().save(bean)
        if(salida>0) {
            var obj = LoginController().findUsuario(cro)
            database.getReference().child("users").child(obj?.cod.toString()).setValue(obj)
            showAlert("Gracias por registrarse")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        else{
            showAlert("Error en el registro")
        }

    }
    fun validar(){
        val usu = txtUsuario.text.toString()
        val cro = txtCorreo.text.toString()
        val cl1 = txtClave.text.toString()
        val cl2 = txtClave2.text.toString()
        var vUsu = LoginController().findUsuarioByUser(usu)
        var vCro = LoginController().findUsuarioByMail(cro)
        if (usu.isEmpty() || cro.isEmpty() || cl1.isEmpty() || cl2.isEmpty()) {
            showAlert("Complete campos faltantes")
            return
        }
        var msg = ""
        if (vUsu!=-1) msg = msg + "Usuario ya existe "
        if (vCro!=-1) msg = msg + "Correo ya existe"
        if (vUsu!=-1 || vCro!=-1){
            showAlert(msg)
            return
        }
        if (cl1.trim()!=cl2.trim()){
            showAlert("Claves no coinciden")
            return
        } else {
            registro()
        }
    }
    fun goLogin(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Advertencia")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}