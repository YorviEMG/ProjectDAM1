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
import com.example.appproject.adaptador.JuegoComboAdapter
import com.example.appproject.adaptador.JugadorComboAdapter
import com.example.appproject.adaptador.TorneoComboAdapter
import com.example.appproject.entidad.Inscripcion
import com.example.appproject.entidad.Juego
import com.example.appproject.entidad.Jugador
import com.example.appproject.entidad.Registro
import com.example.appproject.service.ApiServiceInscripcion
import com.example.appproject.service.ApiServiceRegistro
import com.example.appproject.services.ApiServicesJuego
import com.example.appproject.services.ApiServicesJugador
import com.example.appproject.utils.ApiUtils
import com.example.appproject.utils.AppConfig
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivityMain:AppCompatActivity() {
    //
    private lateinit var txtTorneo:TextInputEditText
    private lateinit var txtJugador:TextInputEditText
    private lateinit var txtJuego:TextInputEditText
    private lateinit var txtEstado:TextInputEditText
    private lateinit var btnGuardar:Button
    private lateinit var btnCancelar:Button
    private lateinit var btnVolver:Button
    //
    private lateinit var apiReg:ApiServiceRegistro
    private lateinit var apiTor:ApiServiceInscripcion
    private lateinit var apiJug:ApiServicesJugador
    private lateinit var apiJue:ApiServicesJuego
    //combo
    private lateinit var spnTorneo:Spinner
    private lateinit var spnJugador:Spinner
    private lateinit var spnJuego:Spinner
    private lateinit var torneos: MutableList<Inscripcion>
    private lateinit var jugadores: MutableList<Jugador>
    private lateinit var juegos: MutableList<Juego>
    private lateinit var torneoComboAdapter: TorneoComboAdapter
    private lateinit var jugadorComboAdapter: JugadorComboAdapter
    private lateinit var juegoComboAdapter: JuegoComboAdapter
    private var idTorneo:Int = 0
    private var idJugador:Int = 0
    private var idJuego:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //txtTorneo=findViewById(R.id.txtRegistroTorneo)
        /*txtJugador=findViewById(R.id.txtRegistroJugador)
        txtJuego=findViewById(R.id.txtRegistroJuego)*/
        txtEstado=findViewById(R.id.txtEstado)
        btnGuardar=findViewById(R.id.btnGuardarRegistro)
        btnCancelar=findViewById(R.id.btnCancelarRegistro)
        btnVolver=findViewById(R.id.btnVolverRegistro)
        apiReg=ApiUtils.getAPIServiceRegistro()
        apiTor=ApiUtils.getAPIServiceInscripcion()
        apiJug=ApiUtils.getAPIServiceJugador()
        apiJue=ApiUtils.getAPIServiceJuego()
        //
        btnGuardar.setOnClickListener { guardar() }
        btnCancelar.setOnClickListener { cancelar() }
        btnVolver.setOnClickListener { volver() }
        //combo
        spnTorneo = findViewById(R.id.spnTorneo)
        spnJugador = findViewById(R.id.spnJugad)
        spnJuego = findViewById(R.id.spnJuego)

        torneos = mutableListOf()
        jugadores = mutableListOf()
        juegos = mutableListOf()

        torneoComboAdapter = TorneoComboAdapter(this, torneos)
        spnTorneo.adapter = torneoComboAdapter

        jugadorComboAdapter = JugadorComboAdapter(this, jugadores)
        spnJugador.adapter = jugadorComboAdapter

        juegoComboAdapter = JuegoComboAdapter(this, juegos)
        spnJuego.adapter = juegoComboAdapter

        apiTor.findAll().enqueue(object :Callback<List<Inscripcion>>{
            override fun onResponse(call: Call<List<Inscripcion>>, response: Response<List<Inscripcion>>) {
                //
                if (response.isSuccessful){
                    val nuevosTorneos = response.body()!!
                    torneoComboAdapter.clear()
                    torneoComboAdapter.addAll(nuevosTorneos )
                    torneoComboAdapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<List<Inscripcion>>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
        apiJug.findAll().enqueue(object :Callback<List<Jugador>>{
            override fun onResponse(call: Call<List<Jugador>>, response: Response<List<Jugador>>) {
                //
                if (response.isSuccessful){
                    val nuevosJugadores = response.body()!!
                    jugadorComboAdapter.clear()
                    jugadorComboAdapter.addAll(nuevosJugadores )
                    jugadorComboAdapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<List<Jugador>>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
        apiJue.findAll().enqueue(object :Callback<List<Juego>>{
            override fun onResponse(call: Call<List<Juego>>, response: Response<List<Juego>>) {
                //
                if (response.isSuccessful){
                    val nuevosJuegos = response.body()!!
                    juegoComboAdapter.clear()
                    juegoComboAdapter.addAll(nuevosJuegos )
                    juegoComboAdapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<List<Juego>>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })

        spnTorneo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position) as Inscripcion
                // Puedes usar selectedItem.id para obtener el ID
                idTorneo = selectedItem.id
                Toast.makeText(
                    AppConfig.CONTEXT,
                    "Selected ID: ${selectedItem.id}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
        spnJugador.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position) as Jugador
                // Puedes usar selectedItem.id para obtener el ID
                idJugador = selectedItem.id
                Toast.makeText(AppConfig.CONTEXT,
                    "Selected ID: ${selectedItem.id}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
        spnJuego.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position) as Juego
                // Puedes usar selectedItem.id para obtener el ID
                idJuego = selectedItem.id
                Toast.makeText(AppConfig.CONTEXT,
                    "Selected ID: ${selectedItem.id}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
    }
    fun cancelar(){
        val intent = Intent(this, RegistroActivityMain::class.java)
        startActivity(intent)
            }
    fun volver(){
        val intent = Intent(this, ListaRegistroActivityMain::class.java)
        startActivity(intent)
    }
    fun guardar(){
        /*val to = txtTorneo.text.toString().toIntOrNull()?:0
        val ju = txtJugador.text.toString().toIntOrNull()?:0
        val jue = txtJuego.text.toString().toIntOrNull()?:0*/
        val est = txtEstado.text.toString().toIntOrNull()?:0

        val bean = Registro( 0,idTorneo,"", idJugador,"", idJuego,"",est, "")

        apiReg.save(bean).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    var obj=response.body()!!
                    showAlert(obj)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
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