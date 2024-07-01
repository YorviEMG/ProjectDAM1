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
import com.example.appproject.entidad.Juego
import com.example.appproject.service.ApiServicesJuego
import com.example.appproject.utils.ApiUtils

import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JuegoActualizarActivity : AppCompatActivity() {

    private lateinit var txtCodigo: TextInputEditText
    private lateinit var txtNombre: TextInputEditText
    private lateinit var txtPlataforma: TextInputEditText
    private lateinit var txtDesarrollador: TextInputEditText
    private lateinit var txtIdCategoria: TextInputEditText
    private lateinit var btnActualizarJuego: Button
    private lateinit var btnEliminarJuego: Button


    //
    private lateinit var api: ApiServicesJuego

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.actualizar_juego_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtCodigo = findViewById(R.id.txtActualizarCodigoJuego)
        txtNombre = findViewById(R.id.txtActualizarNombreJuego)
        txtPlataforma = findViewById(R.id.txtActualizarPlataformaJuego)
        txtDesarrollador = findViewById(R.id.txtActualizarDesarrolladorJuego)
        txtIdCategoria = findViewById(R.id.txtActualizarCategoriaJuego)
        btnActualizarJuego = findViewById(R.id.btnActualizarJuego)
        btnEliminarJuego = findViewById(R.id.btnEliminarJuego)
        //
        api=ApiUtils.getAPIServiceJuego()

        btnActualizarJuego.setOnClickListener { grabar() }
        btnEliminarJuego.setOnClickListener { eliminar() }
        datos()
    }
    fun eliminar(){
        var cod=txtCodigo.text.toString().toInt()
        showAlertConfirm("Seguro de eliminar Juego con ID : "+cod,cod)
    }
    fun grabar(){
        //leer controles
        var cod=txtCodigo.text.toString().toInt()
        var nom=txtNombre.text.toString()
        var plat=txtPlataforma.text.toString()
        var des=txtDesarrollador.text.toString()
        var cat=txtIdCategoria.text.toString().toInt()
        var bean=Juego(cod,nom,plat,des,cat)
        //invocar a la función update
        api.update(bean).enqueue(object:Callback<Juego>{
            override fun onResponse(call: Call<Juego>, response: Response<Juego>) {
                if(response.isSuccessful){
                    showAlert("Jugador Actualizado")
                }
            }
            override fun onFailure(call: Call<Juego>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }
    fun cerrar(){
        var intent=Intent(this,ListaJuegoActivity::class.java)
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
                        showAlert("Juego eliminado")
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
        api.findById(info.getInt("codigo")).enqueue(object:Callback<Juego>{
            override fun onResponse(call: Call<Juego>, response: Response<Juego>) {
                if(response.isSuccessful){
                    var obj=response.body()!!
                    txtCodigo.setText(obj.Id.toString())
                    txtNombre.setText(obj.nombre)
                    txtPlataforma.setText(obj.plataforma)
                    txtDesarrollador.setText(obj.desarrollador)
                    txtIdCategoria.setText(obj.IdCategoria).toString()

                }
            }
            override fun onFailure(call: Call<Juego>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }
}