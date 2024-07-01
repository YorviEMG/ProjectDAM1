package com.example.appproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.entidad.Jugador
import com.example.appproject.service.ApiServicesJugador
import com.example.appproject.utils.ApiUtils

import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JugadorActualizarActivity : AppCompatActivity() {

    private lateinit var txtCodigo: TextInputEditText
    private lateinit var txtNombre: TextInputEditText
    private lateinit var txtApellido: TextInputEditText
    private lateinit var txtEdad: TextInputEditText
    private lateinit var txtNacionalidad: TextInputEditText
    private lateinit var btnActualizarJugador: Button
    private lateinit var btnEliminarJugador: Button


    //
    private lateinit var api: ApiServicesJugador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.actualizar_jugador_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtCodigo = findViewById(R.id.txtActualizarCodigoJugador)
        txtNombre = findViewById(R.id.txtActualizarNombreJugador)
        txtApellido = findViewById(R.id.txtActualizarApellidoJugador)
        txtEdad = findViewById(R.id.txtActualizarEdadJugador)
        txtNacionalidad = findViewById(R.id.txtActualizarNacionalidadJugador)
        btnActualizarJugador = findViewById(R.id.btnActualizarJugador)
        btnEliminarJugador = findViewById(R.id.btnEliminarJugador)
        //
        api=ApiUtils.getAPIServiceJugador()

        btnActualizarJugador.setOnClickListener { grabar() }
        btnEliminarJugador.setOnClickListener { eliminar() }
        datos()
    }
    fun eliminar(){
        var cod=txtCodigo.text.toString().toInt()
        showAlertConfirm("Seguro de eliminar Jugador con ID : "+cod,cod)
    }
    fun grabar(){
        //leer controles
        var cod=txtCodigo.text.toString().toInt()
        var nom=txtNombre.text.toString()
        var ape=txtApellido.text.toString()
        var edad=txtEdad.text.toString()
        var nac=txtNacionalidad.text.toString()
        var bean=Jugador(cod,nom,ape,edad,nac)
        //invocar a la función update
        api.update(bean).enqueue(object:Callback<Jugador>{
            override fun onResponse(call: Call<Jugador>, response: Response<Jugador>) {
                if(response.isSuccessful){
                    showAlert("Medicamento Actualizado")
                }
            }
            override fun onFailure(call: Call<Jugador>, t: Throwable) {
              showAlert(t.localizedMessage)
            }
        })
    }
    fun cerrar(){
        var intent=Intent(this,ListaJugadorActivity::class.java)
        startActivity(intent)
    }
    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
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
            api.deleteById(cod).enqueue(object: Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        showAlert("Jugador eliminado")
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    showAlert(t.localizedMessage)
                }
            })
        })
        builder.setNegativeButton  ("Cancelar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun datos(){
        //recuperar clave
        var info=intent.extras!!
        //invocar a la función findById
        api.findById(info.getInt("codigo")).enqueue(object:Callback<Jugador>{
            override fun onResponse(call: Call<Jugador>, response: Response<Jugador>) {
                if(response.isSuccessful){
                    var obj=response.body()!!
                    txtCodigo.setText(obj.Id.toString())
                    txtNombre.setText(obj.nombre)
                    txtApellido.setText(obj.apellido)
                    txtEdad.setText(obj.edad)
                    txtNacionalidad.setText(obj.nacionalidad)

                }
            }
            override fun onFailure(call: Call<Jugador>, t: Throwable) {
               showAlert(t.localizedMessage)
            }
        })
    }
}