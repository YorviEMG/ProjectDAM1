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
import com.example.appproject.adaptador.JugadorAdapter
import com.example.appproject.entidad.Jugador
import com.example.appproject.services.ApiServicesJugador
import com.example.appproject.utils.ApiUtils
import com.example.appproject.utils.AppConfig

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaJugadorActivity : AppCompatActivity() {
    private lateinit var rvJugador:RecyclerView
    private lateinit var btnNuevo:Button
    private lateinit var btnVolverMenu:Button

    //declarar atributo de la interfaza ApiServicesMedicamento
    private lateinit var apiJugador: ApiServicesJugador


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_jugador_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvJugador=findViewById(R.id.rvJugador)
        btnNuevo=findViewById(R.id.btnNuevoJugador)
        btnVolverMenu=findViewById(R.id.btnVolverJugadorLista)
        btnVolverMenu.setOnClickListener { volverMenu() }
        btnNuevo.setOnClickListener { nuevo() }
        //instanciar el atributo apiMedicamento
        apiJugador=ApiUtils.getAPIServiceJugador()
        listar()
    }

    fun volverMenu(){
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun nuevo(){
        var intent=Intent(this,RegistrarJugadorActivity::class.java)
        startActivity(intent)
    }
    fun listar(){
        //acceder a la función findAll
        apiJugador.findAll().enqueue(object :Callback<List<Jugador>>{
            override fun onResponse(call: Call<List<Jugador>>,response: Response<List<Jugador>>) {
               //validar estado
                if(response.isSuccessful){
                    var data= response.body()
                    var adaptador= JugadorAdapter(data!!)
                    rvJugador.adapter=adaptador
                    rvJugador.layoutManager= LinearLayoutManager(AppConfig.CONTEXT)
                }
            }
            override fun onFailure(call: Call<List<Jugador>>, t: Throwable) {
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