package com.example.act2_2p

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.google.android.material.snackbar.Snackbar

class AgregarCampeon : AppCompatActivity() {
    private lateinit var tvTitulo: TextView
    private  lateinit var edANombre: EditText
    private  lateinit var etARegion: EditText
    private  lateinit var etARol: EditText
    private  lateinit var butt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_campeon)
        inicializarVistas()
        butt.setOnClickListener{
            insertarJuego( edANombre.text.toString(),  etARegion.text.toString(), etARol.text.toString())
        }
    }
    val columnaID = "id"
    val columnaNombre = "nombre"
    val columnaRegion = "region"
    val columnaRol = "rol"
    var id: Int = 0
    private fun insertarJuego(nombreCampeon: String, nombreRegion: String, nombreRol: String){
        if(!TextUtils.isEmpty(nombreRol)) {
            val baseDatos = BaseDatos(this)
            //  val columnas = arrayOf(columnaID, columnaNombreJuego, columnaPrecio, columnaConsola)
            val contenido = ContentValues()
            contenido.put(columnaNombre, nombreCampeon)
            contenido.put(columnaRegion, nombreRegion)
            contenido.put(columnaRol, nombreRol)
            //guardar imagen
            id = baseDatos.insertar(contenido).toInt()
            if (id > 0) {
                Toast.makeText(this, "campeon " + nombreCampeon + " agregado", Toast.LENGTH_LONG).show()
                finish()
            } else
                Toast.makeText(this, "Ups no se pudo guardar el campeon", Toast.LENGTH_LONG).show()
            baseDatos.cerrarConexion()
        }else{

        }
    }
    private fun inicializarVistas(){
        edANombre = findViewById(R.id.edANombre)
        butt = findViewById(R.id.button)
        etARegion = findViewById(R.id.etARegion)
        etARol = findViewById(R.id.etARol)
    }
}