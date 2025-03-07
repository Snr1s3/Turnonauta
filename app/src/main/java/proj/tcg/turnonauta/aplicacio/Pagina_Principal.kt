package proj.tcg.turnonauta.aplicacio

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class Pagina_Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_principal)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
    }
}