package proj.tcg.turnonauta.registre

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class Registre : AppCompatActivity() {
    private lateinit var bRegistre : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registre)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        bRegistre = findViewById(R.id.bRegistre)

        bRegistre.setOnClickListener{
            val intent = Intent(this, PantallaLogin::class.java)
            startActivity(intent)
        }
    }
}