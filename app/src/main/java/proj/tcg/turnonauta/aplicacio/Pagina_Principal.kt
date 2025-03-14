package proj.tcg.turnonauta.aplicacio

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.config.Configuracio
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class Pagina_Principal : AppCompatActivity() {
    private lateinit var botonConfig : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_principal)
       
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        botonConfig = findViewById<ImageButton>(R.id.boton_config)

        botonConfig.setOnClickListener {
            val intent = Intent(this, Configuracio::class.java)
            startActivity(intent)
        }
    }
}
