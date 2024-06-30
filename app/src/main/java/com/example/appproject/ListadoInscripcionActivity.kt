package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.adaptador.InscripcionAdapter
import com.example.appproject.entidad.Inscripcion
import com.example.appproject.service.ApiServiceInscripcion
import com.example.appproject.utils.ApiUtils
import com.example.appproject.utils.AppConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListadoInscripcionActivity:AppCompatActivity() {
    private lateinit var rvInscripcion:RecyclerView
    private lateinit var btnNuevo:Button
    //private lateinit var searchView: SearchView
    //private lateinit var inscripcion: List<Inscripcion>
    //private lateinit var adapter: InscripcionAdapter
    //
    private lateinit var apiIns:ApiServiceInscripcion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_inscripcion_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvInscripcion=findViewById(R.id.rbInscripcion)
        btnNuevo=findViewById(R.id.btnNuevaInscripcion)
        //searchView=findViewById(R.id.searchView)
        apiIns=ApiUtils.getAPIServiceInscripcion()
        //
        btnNuevo.setOnClickListener { nuevo() }
        listar()

        /*searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               filter(newText)
                return true
            }
        })*/
    }

    fun nuevo(){
        var intent= Intent(this, InscripcionActivity::class.java)
        startActivity(intent)
    }
    fun listar(){
        apiIns.findAll().enqueue(object :Callback<List<Inscripcion>>{
            override fun onResponse(call: Call<List<Inscripcion>>, response: Response<List<Inscripcion>>) {
               //
                if (response.isSuccessful){
                    var data= response.body()
                    var adaptador= InscripcionAdapter(data!!)
                    rvInscripcion.adapter=adaptador
                    rvInscripcion.layoutManager=LinearLayoutManager(AppConfig.CONTEXT)
                }
            }
            override fun onFailure(call: Call<List<Inscripcion>>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }
    fun filter(text: String?) {
    /*  val filteredList = inscripcion.filter {
            (it.nombre?.contains(text ?: "", ignoreCase = true) == true) ||
                    (it.Categoria?.contains(text ?: "", ignoreCase = true) == true)
        }
        adapter = InscripcionAdapter(filteredList)
        rvInscripcion.adapter = adapter
        */
    }

    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

}

