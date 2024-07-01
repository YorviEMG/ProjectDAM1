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
import com.example.appproject.adaptador.JuegoAdapter
import com.example.appproject.entidad.Juego
import com.example.appproject.services.ApiServicesJuego
import com.example.appproject.utils.ApiUtils
import com.example.appproject.utils.AppConfig

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaJuegoActivity : AppCompatActivity() {
    private lateinit var rvJuego:RecyclerView
    private lateinit var btnNuevo:Button
    private lateinit var btnVolverMenu:Button

    //declarar atributo de la interfaza ApiServicesMedicamento
    private lateinit var apiJuego: ApiServicesJuego


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_juego_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvJuego=findViewById(R.id.rvJuego)
        btnNuevo=findViewById(R.id.btnNuevoJuego)
        btnVolverMenu=findViewById(R.id.btnVolverJuegoLista)
        btnVolverMenu.setOnClickListener { volverMenu() }
        btnNuevo.setOnClickListener { nuevo() }
        //instanciar el atributo apiMedicamento
        apiJuego=ApiUtils.getAPIServiceJuego()
        listar()
    }

    fun volverMenu(){
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun nuevo(){
        var intent=Intent(this,RegistrarJuegoActivity::class.java)
        startActivity(intent)
    }
    fun listar(){
        //acceder a la función findAll
        apiJuego.findAll().enqueue(object :Callback<List<Juego>>{
            override fun onResponse(call: Call<List<Juego>>,response: Response<List<Juego>>) {
                //validar estado
                if(response.isSuccessful){
                    var data= response.body()
                    var adaptador= JuegoAdapter(data!!)
                    rvJuego.adapter=adaptador
                    rvJuego.layoutManager= LinearLayoutManager(AppConfig.CONTEXT)
                }
            }
            override fun onFailure(call: Call<List<Juego>>, t: Throwable) {
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