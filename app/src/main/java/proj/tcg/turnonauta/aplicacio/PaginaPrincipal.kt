package proj.tcg.turnonauta.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.config.Configuracio
import proj.tcg.turnonauta.models.UsuarisStatistics
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException

class PaginaPrincipal : AppCompatActivity() {
    private lateinit var botonConfig : ImageButton
    private var userId: Int = 0
    private lateinit var response: UsuarisStatistics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_principal)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        val appInstance = AppTurnonauta.getInstance()
        userId = appInstance.getUserIdApp()
        // Log.d("User_ID Login:", "user ID: "+userId)
        getDataUser()
        botonConfig = findViewById(R.id.boton_config)

        botonConfig.setOnClickListener {
            val intent = Intent(this, Configuracio::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDataUser(){
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getStatistic(userId)
                Log.d("User_ID Pantalla d'Inici:", "ID: $response")
                val idText = findViewById<TextView>(R.id.idUser)
                val nomText = findViewById<TextView>(R.id.nomUser)
                val rJ = findViewById<TextView>(R.id.rJ)
                val rG = findViewById<TextView>(R.id.rG)
                val tJ = findViewById<TextView>(R.id.tJ)
                val tG = findViewById<TextView>(R.id.tG)
                idText.text = response.id.toString()
                nomText.text = response.username
                rJ.text = response.roundsPlayed.toString()
                rG.text = response.roundsWon.toString()
                tJ.text = response.tournamentsPlayed.toString()
                tG.text = response.tournamentsWon.toString()
            } catch (e: HttpException) {
                Toast.makeText(this@PaginaPrincipal, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@PaginaPrincipal, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@PaginaPrincipal,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
