package com.example.act2_2p

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class EditarCampeon : AppCompatActivity() {
    private lateinit var tvTitulo: TextView
    private  lateinit var edANombre: EditText
    private  lateinit var etARegion: EditText
    private  lateinit var etARol: EditText
    private  lateinit var butt: Button
    var campeon: Campeon? = null
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_campeon)
        inicializarVistas()
        id = intent.getIntExtra("id", 0)
        buscarCampeon(id)
        poblarCampos()
    }
    private fun poblarCampos() {
        edANombre.setText(campeon?.nombre)
        etARegion.setText(campeon?.region)
        etARol.setText(campeon?.rol)
    }

    private fun inicializarVistas() {
        edANombre = findViewById(R.id.edANombre)
        etARegion = findViewById(R.id.etARegion)
        etARol = findViewById(R.id.etARol)
        butt = findViewById(R.id.button)
        butt.setOnClickListener {
            actualizarCampeon(edANombre.text.toString(),etARegion.text.toString(),etARol.text.toString())
            val s = Intent(this, MainActivity::class.java)
            startActivity(s)
            finish()
        }
    }
    val columnaNombre = "nombre"
    val columnaRegion = "region"
    val columnaRol = "rol"
    private fun actualizarCampeon(nombreCampeon: String, nombreRegion: String, nombreRol: String) {
        if (!TextUtils.isEmpty(nombreRol)) {
            val baseDatos = BaseDatos(this)
            val contenido = ContentValues()
            contenido.put(columnaNombre, nombreCampeon)
            contenido.put(columnaRegion, nombreRegion)
            contenido.put(columnaRol, nombreRol)
            if ( id > 0) {
                val argumentosWhere = arrayOf(id.toString())
                val id_actualizado = baseDatos.actualizar(contenido, "id = ?", argumentosWhere)
                if (id_actualizado > 0) {
                    Toast.makeText(this, "Campeon actualizado", Toast.LENGTH_LONG).show()
                } else {
                    val alerta = AlertDialog.Builder(this)
                    alerta.setTitle("Atención")
                        .setMessage("No fue posible actualizarlo")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar") { dialog, which ->

                        }
                        .show()
                }
            } else {
                Toast.makeText(this, "no existe id", Toast.LENGTH_LONG).show()
            }
            baseDatos.cerrarConexion()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("Range")
    private fun buscarCampeon(idCampeon: Int) {

        if (idCampeon > 0) {
            val baseDatos = BaseDatos(this)
            val columnasATraer = arrayOf("id", "nombre", "region", "rol")
            val condicion = " id = ?"
            val argumentos = arrayOf(idCampeon.toString())
            val ordenarPor = "id"
            val cursor = baseDatos.seleccionar(columnasATraer, condicion, argumentos, ordenarPor)

            if (cursor.moveToFirst()) {
                do {
                    val campeon_id = cursor.getInt(cursor.getColumnIndex("id"))
                    val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                    val region = cursor.getString(cursor.getColumnIndex("region"))
                    val rol = cursor.getString(cursor.getColumnIndex("rol"))
                    campeon = Campeon(campeon_id, nombre, region, rol)
                } while (cursor.moveToNext())
            }
            baseDatos.cerrarConexion()
        }
    }
}