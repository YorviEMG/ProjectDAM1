package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.entidad.Juego
import com.example.appproject.services.ApiServicesJuego
import com.example.appproject.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var txtNombre: TextInputEditText
private lateinit var txtPlataforma: TextInputEditText
private lateinit var txtDesarrollador: TextInputEditText
private lateinit var txtIdCategoria: TextInputEditText
private lateinit var btnRegistrarJuego: Button
private lateinit var btnVolverJuego: Button
private lateinit var btnVolverJugadorLista:Button

private lateinit var api: ApiServicesJuego

class RegistrarJuegoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_juego_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtNombre = findViewById(R.id.txtRegistrarNombreJuego)
        txtPlataforma = findViewById(R.id.txtRegistrarPlataformaJuego)
        txtDesarrollador = findViewById(R.id.txtRegistrarDesarrolladorJuego)
        txtIdCategoria = findViewById(R.id.txtRegistrarCategoriaJuego)
        btnRegistrarJuego = findViewById(R.id.btnRegistrarJuego)
        btnVolverJuego = findViewById(R.id.btnVolverJuego)
        btnVolverJugadorLista = findViewById(R.id.btnVolverJugadorLista)

        api = ApiUtils.getAPIServiceJuego()

        btnVolverJugadorLista.setOnClickListener{volverMenu()}
        btnRegistrarJuego.setOnClickListener { grabar() }
        btnVolverJuego.setOnClickListener { volver() }
    }

    fun volverMenu(){
        var intent=Intent(this,ListaJuegoActivity::class.java)
        startActivity(intent)
    }
    fun grabar() {
        // Leer controles
        val nom = txtNombre.text.toString()
        val plat = txtPlataforma.text.toString()
        val des = txtDesarrollador.text.toString()
        val cat = txtIdCategoria.text.toString().toInt()
        val bean = Juego(0, nom, plat, des, cat)

        // Invocar a la función save
        api.save(bean).enqueue(object: Callback<String> {
            /*override fun onResponse(call: Call<String>, response: Response<Juego>) {
                if(response.isSuccessful){
                    var obj=response.body()!!
                    showAlert("Juego registrado con ID: "+obj.Id)
                }
            }
            override fun onFailure(call: Call<Juego>, t: Throwable) {
                showAlert(t.localizedMessage)
            }*/
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){

                    var obj1 = response.body()!!
                    //showAlert("Respuesta del servidor: $obj1")
                    showAlert(obj1)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MiClase", "Error de red: ${t.localizedMessage}")
                showAlert(t.localizedMessage)
            }
        })
    }

    fun volver() {
        val intent = Intent(this, RegistrarJuegoActivity::class.java)
        startActivity(intent)
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