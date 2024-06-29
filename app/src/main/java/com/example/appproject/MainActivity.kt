package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var btnPerfil: ImageButton
private lateinit var btnLogout: ImageButton
private lateinit var auth: FirebaseAuth
private lateinit var authGoogle: GoogleSignIn
private val callbackManager = CallbackManager.Factory.create()
var usuName:String = ""
var correo:String = ""
var log:String = ""
var rol:String = ""

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
        auth = Firebase.auth
        btnPerfil = findViewById(R.id.btnPerfilMain)
        btnLogout = findViewById(R.id.btnLogoutMain)
        btnPerfil.setOnClickListener{ goPerfil() }
        btnLogout.setOnClickListener{
            auth.signOut()
            objGoogleSignIn.clientGoogle.signOut()
            LoginManager.getInstance().logOut()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        initial()
    }
    fun initial(){
        var info = intent.extras
        if (info != null && info.containsKey("name")) {
            usuName = info.getString("name").toString()
            correo = info.getString("correo").toString()
            log = info.getString("log").toString()
            rol = info.getString("rol").toString()
        }
    }
    fun goPerfil(){
        if (usuName != ""){
            var intent = Intent(this, PerfilActivity::class.java)
            intent.putExtra("name", usuName)
            intent.putExtra("correo", correo)
            intent.putExtra("log", log)
            intent.putExtra("rol", rol)
            startActivity(intent)
        }else{
            Toast.makeText(this, "No existe intent",Toast.LENGTH_SHORT)
        }
    }
}