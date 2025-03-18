package proj.tcg.turnonauta.aplicacio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class FiltresTornejos : AppCompatActivity() {
    private lateinit var guardarButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filtres_tornejos)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        guardarButton = findViewById(R.id.guardar)
        guardarButton.setOnClickListener{
            val intent = Intent(this, LlistaTornejosJugats::class.java)
            startActivity(intent)
        }
    }
}