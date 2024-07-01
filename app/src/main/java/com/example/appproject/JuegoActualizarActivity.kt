package com.example.appproject

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproject.adaptador.CategoriaAdapter
import com.example.appproject.entidad.Categoria
import com.example.appproject.entidad.Juego
import com.example.appproject.service.ApiServiceCategoria
import com.example.appproject.services.ApiServicesJuego
import com.example.appproject.utils.ApiUtils
import com.example.appproject.utils.AppConfig

import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JuegoActualizarActivity : AppCompatActivity() {

    private lateinit var txtCodigo: TextInputEditText
    private lateinit var txtNombre: TextInputEditText
    private lateinit var txtPlataforma: TextInputEditText
    private lateinit var txtDesarrollador: TextInputEditText
    //private lateinit var txtIdCategoria: TextInputEditText
    private lateinit var btnActualizarJuego: Button
    private lateinit var btnEliminarJuego: Button
    private lateinit var btnVolverActualizarJugadorLista:Button
    private lateinit var spinner: Spinner

    //
    private lateinit var api: ApiServicesJuego
    //cate
    private lateinit var apiCombo: ApiServiceCategoria
    private lateinit var categorias: MutableList<Categoria>
    private lateinit var categoriaAdapter: CategoriaAdapter
    private var idCate:Int = 0

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
        //txtIdCategoria = findViewById(R.id.txtActualizarCategoriaJuego)
        btnActualizarJuego = findViewById(R.id.btnActualizarJuego)
        btnEliminarJuego = findViewById(R.id.btnEliminarJuego)
        btnVolverActualizarJugadorLista = findViewById(R.id.btnVolverActualizarJugadorLista)
        //
        api=ApiUtils.getAPIServiceJuego()
        apiCombo = ApiUtils.getAPIServiceCategoria()

        btnVolverActualizarJugadorLista.setOnClickListener{volver()}
        btnActualizarJuego.setOnClickListener { grabar() }
        btnEliminarJuego.setOnClickListener { eliminar() }
        datos()

        spinner = findViewById(R.id.spnCateJueAct)
        categorias = mutableListOf()

        categoriaAdapter = CategoriaAdapter(this, categorias)
        spinner.adapter = categoriaAdapter

        apiCombo.findAll().enqueue(object :Callback<List<Categoria>>{
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                //
                if (response.isSuccessful){
                    val nuevasCategorias = response.body()!!
                    categoriaAdapter.clear()
                    categoriaAdapter.addAll(nuevasCategorias )
                    categoriaAdapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position) as Categoria
                // Puedes usar selectedItem.id para obtener el ID
                idCate = selectedItem.idCategoria
                Toast.makeText(
                    AppConfig.CONTEXT,
                    "Selected ID: ${selectedItem.idCategoria}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
    }

    fun volver(){
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
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
        //var cat=txtIdCategoria.text.toString().toInt()
        var bean=Juego(cod,nom,plat,des,idCate, "")
        //invocar a la función update
        api.update(bean).enqueue(object:Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    showAlert("Juego Actualizado")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
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
                    txtCodigo.setText(obj.id.toString())
                    txtNombre.setText(obj.nombre)
                    txtPlataforma.setText(obj.plataforma)
                    txtDesarrollador.setText(obj.desarrollador)
                    seleccionarCategoria(obj.idCategoria)

                }
            }
            override fun onFailure(call: Call<Juego>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }
    private fun seleccionarCategoria(categoriaId: Int) {
        val position = categorias.indexOfFirst { it.idCategoria == categoriaId }
        if (position != -1) {
            spinner.setSelection(position)
        }
    }
}