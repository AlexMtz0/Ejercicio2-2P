package com.example.act2_2p

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos {
    val nombreBaseDatos = "MisCampeones"
    val tablaJuegos = "campeones"
    val columnaID = "id"
    val columnaNombre = "nombre"
    val columnaRegion = "region"
    val columnaRol = "rol"

    val version = 1

    val creacionTablaCampeon = "CREATE TABLE IF NOT EXISTS "+tablaJuegos +
            "(  " + columnaID + " INTEGER PRIMARY KEY AUTOINCREMENT," + //nombre columna y tipo de dato
            "  " + columnaNombre + " TEXT NOT NULL," +
            "  " + columnaRegion + " TEXT," +
            "  " + columnaRol + " TEXT)"

    var misQuerys: SQLiteDatabase

    constructor(contexto: Context){
        val baseDatos = MiDBHelper(contexto)
        misQuerys = baseDatos.writableDatabase
    }
    inner class MiDBHelper(contexto: Context): SQLiteOpenHelper(contexto, nombreBaseDatos, null, version){
        override fun onCreate(p0: SQLiteDatabase?) {
            //aqui crearmos nuestras tablas de la db
            if (p0 != null) {
                p0.execSQL(creacionTablaCampeon)
            }//queries de creación
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            //para realizar migraciones
            p0?.execSQL("DROP TABLE IF EXISTS "+tablaJuegos)
        }
    }

    fun insertar(values: ContentValues): Long{
        return misQuerys.insert(tablaJuegos, null, values)
    }

    fun actualizar(values: ContentValues, clausulaWhere: String, argumentosWhere: Array<String>): Int{
        return misQuerys.update(tablaJuegos,values,clausulaWhere, argumentosWhere )
    }

    fun eliminar( clausulaWhere: String, argumentosWhere: Array<String>): Int {
        return misQuerys.delete(tablaJuegos, clausulaWhere, argumentosWhere)
    }
    fun seleccionar(columnasATraer: Array<String>, condiciones: String, argumentos: Array<String>, ordenarPor: String ): Cursor {
        val groupBy:String? = null
        val having:String? = null
        // val consulta = SQLiteQueryBuilder()
        // consulta.tables = tablaJuegos
        val cursor =  misQuerys.query(tablaJuegos, columnasATraer,condiciones,argumentos, groupBy, having, ordenarPor)
        return cursor
    }

    fun traerTodos(columnasATraer: Array<String>, ordenarPor: String ): Cursor {
        val groupBy:String? = null
        val having:String? = null
        // val consulta = SQLiteQueryBuilder()
        // consulta.tables = tablaJuegos
        val cursor =  misQuerys.query(tablaJuegos, columnasATraer,null,null, groupBy, having, ordenarPor)
        return cursor
    }

    fun cerrarConexion(){
        misQuerys.close()
    }
}