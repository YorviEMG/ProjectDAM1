package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.entidad.Inscripcion
import com.example.appproject.service.ApiServiceInscripcion
import com.example.appproject.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class InscripcionActivity : AppCompatActivity() {
    private lateinit var txtNombre: TextInputEditText
    private lateinit var txtFechaIn: TextInputEditText
    private lateinit var txtFechaFin: TextInputEditText
    private lateinit var txtCategoria: TextInputEditText
    private lateinit var btnInscribir: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnVolver: Button
    //
    private lateinit var api: ApiServiceInscripcion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.inscripcion_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtNombre = findViewById(R.id.txtNombre)
        txtFechaIn = findViewById(R.id.txtFechaIn)
        txtFechaFin = findViewById(R.id.txtFechaFin)
        txtCategoria = findViewById(R.id.txtCategoria)
        btnInscribir = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnVolver = findViewById(R.id.btnVolverInscripcion)
        api = ApiUtils.getAPIServiceInscripcion()

        btnVolver.setOnClickListener { volver() }
        btnInscribir.setOnClickListener { guardar() }
        btnCancelar.setOnClickListener { cancelar() }
    }

    fun guardar() {
        var nom = txtNombre.text.toString()
        var ini = txtFechaIn.text.toString()
        var fin = txtFechaFin.text.toString()
        var cat= txtCategoria.text.toString().toIntOrNull() ?:0
        var bean = Inscripcion(0, nom, ini, fin, cat)

        api.save(bean).enqueue(object : Callback<Inscripcion> {
            override fun onResponse(call: Call<Inscripcion>, response: Response<Inscripcion>) {
                if (response.isSuccessful) {
                    var obj = response.body()!!
                    showAlert("Inscripción registrada con ID: " + obj.id)
                }else{
                    showAlert("Error al registrar: "+ response.message())
                }
            }

            override fun onFailure(call: Call<Inscripcion>, t: Throwable) {
                showAlert("Error de red: " + t.localizedMessage)
            }
        })
    }
    private fun cancelar() {
        val intent = Intent(this, InscripcionActivity::class.java)
        startActivity(intent)
    }

    private fun volver() {
        val intent = Intent(this, ListadoInscripcionActivity::class.java)
        startActivity(intent)
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}
