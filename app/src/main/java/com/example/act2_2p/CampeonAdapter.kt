package com.example.act2_2p

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CampeonAdapter(contexto: Context, var listadDeCampeon: ArrayList<Campeon>, campeonin: CampeonInterface) : BaseAdapter(){
    var contexto: Context? = contexto
    var campeoninterface: CampeonInterface? = campeonin
    override fun getCount(): Int {
        return listadDeCampeon.size
    }

    override fun getItem(p0: Int): Any {
        return listadDeCampeon[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        //usar vista reciclado para eficientar
        var convertView: View?= p1
        if(convertView == null){
            convertView = View.inflate(contexto, R.layout.activity_interfaz_campeon, null)
        }

        val campeon = listadDeCampeon[p0]

        val miVista = convertView!!
        val tvNombre: TextView = miVista.findViewById(R.id.tvNombre)
        val tvRegion: TextView = miVista.findViewById(R.id.tvRegion)
        val ivBorrar: ImageView = miVista.findViewById(R.id.ivBorrar)
        val ivEditar: ImageView = miVista.findViewById(R.id.ivEditar)
        tvNombre.text = campeon.nombre
        tvRegion.text = campeon.region
        //borrar
        ivBorrar.setOnClickListener(){
            //eliminar
            val baseDatos = BaseDatos(this.contexto!!)
            val argumentosWhere = arrayOf(campeon.id.toString())
            baseDatos.eliminar("id = ? ", argumentosWhere)
            campeoninterface?.campeonEliminado()
        }

        ivEditar.setOnClickListener(){
            //Editar
            campeoninterface?.editarCampeon(campeon)
        }
        return miVista
    }
}