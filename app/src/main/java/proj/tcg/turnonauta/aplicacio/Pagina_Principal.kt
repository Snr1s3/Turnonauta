package proj.tcg.turnonauta.aplicacio

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

class Pagina_Principal : AppCompatActivity() {
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
      //Log.d("User_ID Login:", "user ID: "+userId)
        getDataUser()
        botonConfig = findViewById(R.id.boton_config)

        botonConfig.setOnClickListener {
            val intent = Intent(this, Configuracio::class.java)
            startActivity(intent)
        }
    }

    private fun getDataUser(){
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.API().getStatistic(userId)
                Log.d("User_ID Pantalla d'Inici:", "ID: "+response)
                val idText = findViewById<TextView>(R.id.idUser)
                val nomText = findViewById<TextView>(R.id.nomUser)
                val rJ = findViewById<TextView>(R.id.rJ)
                val rG = findViewById<TextView>(R.id.rG)
                val tJ = findViewById<TextView>(R.id.tJ)
                val tG = findViewById<TextView>(R.id.tG)
                idText.setText(response.id.toString())
                nomText.setText(response.username.toString())
                rJ.setText(response.roundsPlayed.toString())
                rG.setText(response.roundsWon.toString())
                tJ.setText(response.tournamentsPlayed.toString())
                tG.setText(response.tournamentsWon.toString())
            } catch (e: HttpException) {
                Toast.makeText(this@Pagina_Principal, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@Pagina_Principal, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@Pagina_Principal,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
