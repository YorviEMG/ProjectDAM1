package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.entidad.Jugador
import com.example.appproject.services.ApiServicesJugador
import com.example.appproject.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var txtNombre: TextInputEditText
private lateinit var txtApellido: TextInputEditText
private lateinit var txtEdad: TextInputEditText
private lateinit var txtNacionalidad: TextInputEditText
private lateinit var btnRegistrarJugador: Button
private lateinit var btnVolverJugador: Button
private lateinit var btnVolverJugadorLista:Button

private lateinit var api: ApiServicesJugador

class RegistrarJugadorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_jugador_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtNombre = findViewById(R.id.txtRegistrarNombreJugador)
        txtApellido = findViewById(R.id.txtRegistrarApellidoJugador)
        txtEdad = findViewById(R.id.txtRegistrarEdadJugador)
        txtNacionalidad = findViewById(R.id.txtRegistrarNacionalidadJugador)
        btnVolverJugadorLista=findViewById(R.id.btnVolverRegistrarLista)

        btnRegistrarJugador = findViewById(R.id.btnRegistrarJugador)
        btnVolverJugador = findViewById(R.id.btnVolverJugador)

        api = ApiUtils.getAPIServiceJugador()

        btnVolverJugadorLista.setOnClickListener{volverMenu()}
        btnRegistrarJugador.setOnClickListener { grabar() }
        btnVolverJugador.setOnClickListener { volver() }
    }

    fun volverMenu(){
        var intent=Intent(this,ListaJugadorActivity::class.java)
        startActivity(intent)
    }

    fun grabar() {

        if (txtNombre.text.toString().isEmpty()){
            Toast.makeText(this, "Debes ingresar el nombre", Toast.LENGTH_SHORT).show()
            return

        }

        if (txtApellido.text.toString().isEmpty()){
            Toast.makeText(this, "Debes ingresar el apellido", Toast.LENGTH_SHORT).show()
            return

        }

        if (txtEdad.text.toString().isEmpty()){
            Toast.makeText(this, "Debes ingresar la edad", Toast.LENGTH_SHORT).show()
            return

        }

        if (txtNacionalidad.text.toString().isEmpty()){
            Toast.makeText(this, "Debes ingresar la nacionalidad", Toast.LENGTH_SHORT).show()
            return

        }

        // Leer controles
        val nom = txtNombre.text.toString()
        val ape = txtApellido.text.toString()
        val edad = txtEdad.text.toString()
        val nac = txtNacionalidad.text.toString()
        val bean = Jugador( 0,nom, ape, edad, nac)

        // Invocar a la función save
        /*api.save(bean).enqueue(object: Callback<Jugador>{
            override fun onResponse(call: Call<Jugador>, response: Response<Jugador>) {
                if(response.isSuccessful){
                    var obj=response.body()!!
                    showAlert("Medicamento registrado con ID: "+obj.Id)
                }
            }
            override fun onFailure(call: Call<Jugador>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })*/
        api.save(bean).enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    var obj=response.body()!!
                    showAlert(obj)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }

    fun volver() {
        val intent = Intent(this, RegistrarJugadorActivity::class.java)
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
