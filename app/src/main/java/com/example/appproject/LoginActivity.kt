package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.entidad.Usuario
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


private val callbackManager = CallbackManager.Factory.create()
private lateinit var txtUsuario: TextInputEditText
private lateinit var txtClave: TextInputEditText
private lateinit var btnLogin: Button
private lateinit var btnRegistro: Button
private lateinit var btnGoogle: Button
private lateinit var btnFacebook: LoginButton
private lateinit var auth: FirebaseAuth
private lateinit var user: FirebaseUser
private lateinit var database: FirebaseDatabase

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        FacebookSdk.sdkInitialize(applicationContext)
        // Inicialización de Firebase Auth
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance("https://project-game-c3986-default-rtdb.firebaseio.com/")
        txtUsuario  = findViewById(R.id.txtLogin)
        txtClave    = findViewById(R.id.txtClave)
        btnLogin    = findViewById(R.id.btnLogin)
        btnRegistro = findViewById(R.id.btnRegistro)
        btnGoogle   = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnLogin.setOnClickListener{ ingresar()}
        btnRegistro.setOnClickListener{ goRegistro()}
        btnGoogle.setOnClickListener{ logGoogle()}

        btnFacebook.setReadPermissions("email", "public_profile")
        btnFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$result")
                handleFacebookAccessToken(result.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }

        })

    }
    fun ingresar(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun goRegistro(){
        var intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
    fun logGoogle() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    user = auth.currentUser!!
                    assert(user != null)
                    var intent = Intent(this, MainActivity::class.java)
                    val usu = Usuario(usuario = user.displayName.toString(),
                                     correo = user.email.toString(),
                                    nombre = user.providerId.toString())
                    database.getReference().child("users").child(user.uid).setValue(usu)
                    intent.putExtra("name", user.displayName.toString())
                    startActivity(intent)
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
            }
    }
}