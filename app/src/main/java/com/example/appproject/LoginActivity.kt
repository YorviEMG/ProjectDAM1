package com.example.appproject

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.controller.LoginController
import com.example.appproject.entidad.Usuario
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.Arrays

//Tipos de auth
val GOOGLE   = "GOOGLE"
val FACEBOOK = "FACEBOOK"
val CORREO   = "CORREO"

//Roles
val USER  = "USUARIO"
val ADMIN = "ADMINISTRADOR"

val OCULTO = "OCULTO"

object objGoogleSignIn {
    lateinit var clientGoogle: GoogleSignInClient

    fun init(context: Context){
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        clientGoogle = GoogleSignIn.getClient(context, options)
    }
}

private val RC_SIGN_IN:Int = 123
private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
private var showOneTapUI = true
private val callbackManager = CallbackManager.Factory.create()
private lateinit var txtUsuario: TextInputEditText
private lateinit var txtClave: TextInputEditText
private lateinit var btnLogin: Button
private lateinit var btnRegistro: Button
private lateinit var btnGoogle: Button
//private lateinit var btnFacebook: LoginButton
private lateinit var btnFacebook: Button
private lateinit var auth: FirebaseAuth
private lateinit var user: FirebaseUser
private lateinit var database: FirebaseDatabase
//private lateinit var clientGoogle: GoogleSignInClient
//private lateinit var options: GoogleSignInOptions

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
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null && !accessToken.isExpired){
            startActivity(Intent(this, MainActivity::class.java))
        }

        FacebookSdk.sdkInitialize(applicationContext)
        // Inicialización de Firebase Auth
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance("https://project-game-c3986-default-rtdb.firebaseio.com/")
        /*options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        clientGoogle = GoogleSignIn.getClient(this, options)*/
        objGoogleSignIn.init(this)

        txtUsuario  = findViewById(R.id.txtLogin)
        txtClave    = findViewById(R.id.txtClave)
        btnLogin    = findViewById(R.id.btnLogin)
        btnRegistro = findViewById(R.id.btnRegistro)
        btnGoogle   = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)
        btnLogin.setOnClickListener{ validar()}
        btnRegistro.setOnClickListener{ goRegistro()}

        btnGoogle.setOnClickListener{
            var intent:Intent = objGoogleSignIn.clientGoogle.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
        btnFacebook.setOnClickListener{
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
            LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
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

        /*btnFacebook.setReadPermissions("email", "public_profile")
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

        })*/

    }
    fun ingresar(usu:Usuario){

        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", usu.nombre)
        intent.putExtra("correo", usu.correo)
        intent.putExtra("log", usu.log)
        intent.putExtra("rol", usu.rol)
        startActivity(intent)
    }
    fun goRegistro(){
        var intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
    fun validar(){
       var cro = txtUsuario.text.toString()
       var clv = txtClave.text.toString()

        if (cro.isEmpty() || clv.isEmpty()){
            showAlert("Complete campos obligatorios")
            return
        }
        var usu = LoginController().findUsuario(cro)
        if (usu == null) showAlert("Correo ingresado no existe")
        else{
            if (usu.clave == clv) ingresar(usu)
            else {
                showAlert("Contraseña incorrecta")
                return
            }
        }

    }
    fun logGoogle() {
    }
    fun logFacebook() {
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        if (currentUser != null){
            user = currentUser
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", user.displayName)
            intent.putExtra("correo", user.email)
            //intent.putExtra("correo", user.email.toString())

            startActivity(intent)
            finish()
        }
        //updateUI(currentUser)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)

        //google
        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                var acc:GoogleSignInAccount = task.getResult(ApiException::class.java)
                var credGoogle: AuthCredential = GoogleAuthProvider.getCredential(acc.idToken, null)
                auth.signInWithCredential(credGoogle).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        user = auth.currentUser!!
                        assert(user != null)
                        var intent = Intent(this, MainActivity::class.java)
                        val usu = Usuario(
                            cod = 1,
                            nombre = user.displayName.toString(),
                            correo = user.email.toString(),
                            clave = OCULTO,
                            log = GOOGLE,
                            rol = USER)
                        database.getReference().child("users").child(user.uid).setValue(usu)
                        intent.putExtra("name", user.displayName.toString())
                        intent.putExtra("correo", user.email.toString())
                        intent.putExtra("log", usu.log)
                        intent.putExtra("rol", usu.rol)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "signInWithCredential:failure", task.exception)

                    }
                }


            } catch (e: ApiException){
                Log.w(TAG, "signInResult:failed code=" + e.statusCode)
                //e.printStackTrace()
            }
        }

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
                    val usu = Usuario(
                        cod = 1,
                        nombre = user.displayName.toString(),
                        correo = user.email.toString(),
                        clave = OCULTO,
                        log = FACEBOOK,
                        rol = USER)
                    database.getReference().child("users").child(user.uid).setValue(usu)
                    intent.putExtra("name", user.displayName.toString())
                    intent.putExtra("correo", user.email.toString())
                    intent.putExtra("log", usu.log)
                    intent.putExtra("rol", usu.rol)
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
    //utils
    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Advertencia")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
    fun showAlertConfirm(men:String,cod:Int){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener {
                dialogInterface: DialogInterface, i: Int ->
            //invocar función deleteById

        })
        builder.setNegativeButton  ("Cancelar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}