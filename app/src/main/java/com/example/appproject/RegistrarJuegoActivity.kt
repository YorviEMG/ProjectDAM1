package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

private lateinit var txtNombre: TextInputEditText
private lateinit var txtPlataforma: TextInputEditText
private lateinit var txtDesarrollador: TextInputEditText
//private lateinit var txtIdCategoria: TextInputEditText
private lateinit var btnRegistrarJuego: Button
private lateinit var btnVolverJuego: Button
private lateinit var btnVolverJugadorLista:Button

private lateinit var api: ApiServicesJuego
//cate
private lateinit var apiCombo: ApiServiceCategoria
private lateinit var categorias: MutableList<Categoria>
private lateinit var categoriaAdapter: CategoriaAdapter
private var idCate:Int = 0

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
        //txtIdCategoria = findViewById(R.id.txtRegistrarCategoriaJuego)
        btnRegistrarJuego = findViewById(R.id.btnRegistrarJuego)
        btnVolverJuego = findViewById(R.id.btnVolverJuego)
        btnVolverJugadorLista = findViewById(R.id.btnVolverJugadorLista)

        api = ApiUtils.getAPIServiceJuego()
        apiCombo = ApiUtils.getAPIServiceCategoria()

        btnVolverJugadorLista.setOnClickListener{volverMenu()}
        btnRegistrarJuego.setOnClickListener { grabar() }
        btnVolverJuego.setOnClickListener { volver() }

        val spinner = findViewById<Spinner>(R.id.spnCateJue)
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

    fun volverMenu(){
        var intent=Intent(this,ListaJuegoActivity::class.java)
        startActivity(intent)
    }
    fun grabar() {
        // Leer controles
        val nom = txtNombre.text.toString()
        val plat = txtPlataforma.text.toString()
        val des = txtDesarrollador.text.toString()
        //val cat = txtIdCategoria.text.toString().toInt()
        val bean = Juego(0, nom, plat, des, idCate, "")

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