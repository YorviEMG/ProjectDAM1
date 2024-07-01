package com.example.appproject

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
import com.example.appproject.entidad.Inscripcion
import com.example.appproject.service.ApiServiceCategoria
import com.example.appproject.service.ApiServiceInscripcion
import com.example.appproject.utils.ApiUtils
import com.example.appproject.utils.AppConfig
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InscripcionActivity : AppCompatActivity() {
    private lateinit var spnCate: Spinner
    private lateinit var txtNombre: TextInputEditText
    private lateinit var txtFechaIn: TextInputEditText
    private lateinit var txtFechaFin: TextInputEditText
    private lateinit var txtCategoria: TextInputEditText
    private lateinit var btnInscribir: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnVolver: Button
    //
    private lateinit var api: ApiServiceInscripcion
    //cate
    private lateinit var apiCombo: ApiServiceCategoria
    private lateinit var categorias: MutableList<Categoria>
    private lateinit var categoriaAdapter: CategoriaAdapter
    private var idCate:Int = 0
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
        //txtCategoria = findViewById(R.id.txtCategoria)
        btnInscribir = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnVolver = findViewById(R.id.btnVolverInscripcion)
        api = ApiUtils.getAPIServiceInscripcion()
        apiCombo = ApiUtils.getAPIServiceCategoria()


        btnVolver.setOnClickListener { volver() }
        btnInscribir.setOnClickListener { guardar() }
        btnCancelar.setOnClickListener { cancelar() }

        val spinner = findViewById<Spinner>(R.id.spnCateIns)
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
                Toast.makeText(AppConfig.CONTEXT,
                    "Selected ID: ${selectedItem.idCategoria}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
    }
    fun guardar() {
        var nom = txtNombre.text.toString()
        var ini = txtFechaIn.text.toString()
        var fin = txtFechaFin.text.toString()
        //var cat= txtCategoria.text.toString().toIntOrNull() ?:0

        if (nom.isEmpty() || ini.isEmpty() || fin.isEmpty() || idCate == 0) {
            showAlert("Por favor complete todos los campos y seleccione una categoría.")
            return
        }

        var bean = Inscripcion(0, nom, ini, fin,"", idCate)

        /*api.save(bean).enqueue(object : Callback<Inscripcion> {
            override fun onResponse(call: Call<Inscripcion>, response: Response<Inscripcion>) {
                if (response.isSuccessful) {
                    var obj = response.body()!!
                    showAlert("Inscripción registrada con ID: " + obj.id)
                }else{
                    showAlert("Error al registrar: "+ response.message())
                }
            }

            override fun onFailure(call: Call<Inscripcion>, t: Throwable) {
                //showAlert("Error de red: " + t.localizedMessage)
            }
        })*/
        api.save(bean).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    var obj = response.body()!!
                    showAlert(obj)
                }else{
                    showAlert("Error al registrar: "+ response.message())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
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
