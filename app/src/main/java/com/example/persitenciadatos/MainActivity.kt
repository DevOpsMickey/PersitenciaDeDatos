package com.example.persitenciadatos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    val nombreArchivo = "Datos_de_usuario.txt"

    var btnGuardar: Button? = null
    var etDatos: EditText? = null
    var btnLeer: Button? = null
    var tvDatos: TextView? = null

    var etClave: EditText?= null
    var etValor: EditText?= null
    var btnGuardarClave: Button? = null
    var btnLeerClave: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        btnGuardar!!.setOnClickListener {
            val datos = etDatos!!.text.toString()
            if (datos.length == 0) {
                Toast.makeText(this, "INGRESA INFOMACIÓN", Toast.LENGTH_LONG).show()
            } else {
                escribirDatos(datos)
            }
        }

        btnLeer!!.setOnClickListener {
            tvDatos!!.text = leerDatos(nombreArchivo)
        }

        btnLeerClave!!.setOnClickListener {
            val claveABuscar = etClave!!.text.toString()
            val datoEncontrado = getDatos(claveABuscar)
            etValor!!.setText(datoEncontrado)
        }

        btnGuardarClave!!.setOnClickListener {
            val claveAGuardar = etClave!!.text.toString()
            val valorAGuardar = etValor!!.text.toString()

            setDatos(claveAGuardar, valorAGuardar)
        }
    }

    fun escribirDatos(texto: String) {
        val fos: FileOutputStream
        try {
            fos = openFileOutput(nombreArchivo, Context.MODE_PRIVATE)
            fos.write(texto.toByteArray())
            fos.close()
            Log.wtf("Archivo", texto)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun leerDatos(nombreArchivo: String): String {
        val inputStream: InputStream = openFileInput(nombreArchivo)

        val stringResultado = inputStream.bufferedReader().use { it.readLine() }
        return stringResultado
    }


    fun setDatos(clave:String , valor:String){
        val prefs = getPreferences(MODE_PRIVATE)
        prefs.edit().putString(clave,valor).apply()
    }

    fun getDatos(clave:String):String{
        val prefs = getPreferences(MODE_PRIVATE)
        val dato = prefs.getString(clave, "No se encontró ${clave}")
        return dato.toString()
    }

    fun initUI(){
        btnGuardar = findViewById(R.id.btnGuardar)
        etDatos = findViewById(R.id.etDatos)
        btnLeer = findViewById(R.id.btnLeer)
        tvDatos = findViewById(R.id.tvDatos)
        etClave = findViewById(R.id.etClave)
        etValor = findViewById(R.id.etValor)
        btnGuardarClave = findViewById(R.id.btnGuardarClave)
        btnLeerClave = findViewById(R.id.btnLeerClave)
    }
}