package com.example.act2_2p

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.act2_2p.CampeonAdapter

class MainActivity : AppCompatActivity(), CampeonInterface {
    private lateinit var listview: ListView
    private var listadDeCampeon = ArrayList<Campeon>()
    private lateinit var fabAgregar: FloatingActionButton
    private val ORDENAR_POR_NOMBRE : String  = "nombre"
    val columnas = arrayOf("id", "nombre","region", "rol" )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarVistas()
        asignarEventos()
    }
    override fun onResume() {
        super.onResume()
        traerMisCampeones()
    }
    private fun inicializarVistas(){
        listview = findViewById(R.id.listView)
        fabAgregar = findViewById(R.id.fab)
    }
    private fun asignarEventos(){
        fabAgregar.setOnClickListener{
            val intent = Intent(this, AgregarCampeon::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_listado, menu)
        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val manejador = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(manejador.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                buscarCampeon("%" + p0 + "%")
                Toast.makeText(applicationContext, p0, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(TextUtils.isEmpty(p0)){
                    this.onQueryTextSubmit("");
                }
                return false
            }


        })
        return super.onCreateOptionsMenu(menu)
    }
    private fun traerMisCampeones() {
        val baseDatos = BaseDatos(this)
        val cursor = baseDatos.traerTodos(columnas, ORDENAR_POR_NOMBRE)
        recorrerResultados( cursor)
        baseDatos.cerrarConexion()
    }
    @SuppressLint("Range")
    private fun buscarCampeon(nombre: String) {
        val baseDatos = BaseDatos(this)
        val camposATraer = arrayOf(nombre)
        val cursor = baseDatos.seleccionar(columnas,"nombre like ?", camposATraer, ORDENAR_POR_NOMBRE)
        recorrerResultados( cursor)
        baseDatos.cerrarConexion()
    }

    @SuppressLint("Range")
    fun recorrerResultados(cursor : Cursor){
        if(listadDeCampeon.size > 0) {
            listadDeCampeon.clear()
        }

        if(cursor.moveToFirst()){
            do{
                val campeon_id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val region = cursor.getString(cursor.getColumnIndex("region"))
                val rol = cursor.getString(cursor.getColumnIndex("region"))
                val campeon: Campeon
                campeon = Campeon(campeon_id, nombre, region, rol)
                listadDeCampeon.add(campeon)
            }while(cursor.moveToNext())
        }
        val adapter: CampeonAdapter = CampeonAdapter(this, listadDeCampeon,this)
        listview.adapter = adapter
    }


    override fun campeonEliminado() {
        Log.d("PRUEBAS", "juegoEliminado")
        traerMisCampeones()
    }

    override fun editarCampeon(campeon: Campeon) {
        Log.d("PRUEBAS", "editar Campeon "+campeon.id)
        val intent = Intent(this, EditarCampeon::class.java)
        intent.putExtra("id",campeon.id)
        intent.putExtra("nombre",campeon.nombre)
        intent.putExtra("consola",campeon.region)
        startActivity(intent)
    }

}