package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.adaptador.RegistroAdapter
import com.example.appproject.entidad.Registro
import com.example.appproject.service.ApiServiceRegistro
import com.example.appproject.utils.ApiUtils
import com.example.appproject.utils.AppConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaRegistroActivityMain:AppCompatActivity() {
    private lateinit var rvRegistro:RecyclerView
    private lateinit var btnNuevo:Button
    private lateinit var btnVolver:Button
    //
    private lateinit var apiRegistro:ApiServiceRegistro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_registro_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvRegistro=findViewById(R.id.rvRegistro)
        btnNuevo=findViewById(R.id.btnNuevoRegistro)
        btnVolver=findViewById(R.id.btnVolverRegistro)
        apiRegistro=ApiUtils.getAPIServiceRegistro()
        //
        btnNuevo.setOnClickListener { nuevo() }
        btnVolver.setOnClickListener { volver() }
        listar()
    }
    fun volver(){
        var intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun nuevo(){
        var intent= Intent(this,RegistroActivityMain::class.java)
        startActivity(intent)
    }
    fun listar(){
        apiRegistro.findAll().enqueue(object :Callback<List<Registro>>{
            override fun onResponse(
                call: Call<List<Registro>>,response: Response<List<Registro>>) {
                if (response.isSuccessful){
                    var data= response.body()!!
                    var adaptador= RegistroAdapter(data)
                    rvRegistro.adapter=adaptador
                    rvRegistro.layoutManager=LinearLayoutManager(AppConfig.CONTEXT)
                }
            }
            override fun onFailure(call: Call<List<Registro>>, t: Throwable) {
               showAlert(t.localizedMessage)
            }
        })
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