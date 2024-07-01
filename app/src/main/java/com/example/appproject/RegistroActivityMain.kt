package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.entidad.Jugador
import com.example.appproject.entidad.Registro
import com.example.appproject.service.ApiServiceRegistro
import com.example.appproject.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivityMain:AppCompatActivity() {
    //
    private lateinit var txtTorneo:TextInputEditText
    private lateinit var txtJugador:TextInputEditText
    private lateinit var txtJuego:TextInputEditText
    private lateinit var txtEstado:TextInputEditText
    private lateinit var btnGuardar:Button
    private lateinit var btnCancelar:Button
    private lateinit var btnVolver:Button
    //
    private lateinit var apiReg:ApiServiceRegistro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtTorneo=findViewById(R.id.txtRegistroTorneo)
        txtJugador=findViewById(R.id.txtRegistroJugador)
        txtJuego=findViewById(R.id.txtRegistroJuego)
        txtEstado=findViewById(R.id.txtEstado)
        btnGuardar=findViewById(R.id.btnGuardarRegistro)
        btnCancelar=findViewById(R.id.btnCancelarRegistro)
        btnVolver=findViewById(R.id.btnVolverRegistro)
        apiReg=ApiUtils.getAPIServiceRegistro()
        //
        btnGuardar.setOnClickListener { guardar() }
        btnCancelar.setOnClickListener { cancelar() }
        btnVolver.setOnClickListener { volver() }
    }
    fun cancelar(){
        val intent = Intent(this, RegistroActivityMain::class.java)
        startActivity(intent)
            }
    fun volver(){
        val intent = Intent(this, ListaRegistroActivityMain::class.java)
        startActivity(intent)
    }
    fun guardar(){
        val to = txtTorneo.text.toString().toIntOrNull()?:0
        val ju = txtJugador.text.toString().toIntOrNull()?:0
        val jue = txtJuego.text.toString().toIntOrNull()?:0
        val est = txtEstado.text.toString().toIntOrNull()?:0

        val bean = Registro( 0,to,"", ju,"", jue,"",est, "")

        apiReg.save(bean).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    var obj=response.body()!!
                    showAlert(obj)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }
    fun showAlert(men: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}