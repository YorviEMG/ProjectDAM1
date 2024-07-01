package com.example.appproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproject.adaptador.UsuarioAdapter
import com.example.appproject.entidad.Usuario
import com.example.appproject.utils.AppConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ListaUsuarioActivity:AppCompatActivity() {
    private lateinit var BD: DatabaseReference
    private lateinit var btnHome: ImageButton
    private lateinit var rvUsuario:RecyclerView
    private lateinit var usuarios: ArrayList<Usuario>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lista_usuario_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvUsuario=findViewById(R.id.rvUsuarios)
        btnHome=findViewById(R.id.btnHomeUsu)
        btnHome.setOnClickListener{irHome()}
        usuarios=ArrayList<Usuario>()
        conectar()
        listar()
    }
    fun irHome(){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun listar(){
        BD.child("users").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(row in snapshot.children){
                    var bean=row.getValue(Usuario::class.java)
                    usuarios.add(bean!!)
                }
                var adaptador=UsuarioAdapter(usuarios)
                rvUsuario.adapter=adaptador
                rvUsuario.layoutManager=LinearLayoutManager(AppConfig.CONTEXT)
            }
            override fun onCancelled(error: DatabaseError) {
                showAlert(error.message)
            }
        })
    }
    fun conectar(){
        //iniciar Firebase en la clase Actual
        FirebaseApp.initializeApp(this)
        //acceso a la base de datos(instancia BD)
        BD= FirebaseDatabase.getInstance().reference
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