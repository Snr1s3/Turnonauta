package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class ContrasenyaActualitzada : AppCompatActivity() {
    private lateinit var bInici : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contrasenya_actualitzada)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        bInici = findViewById(R.id.bInici)

        bInici.setOnClickListener {
            val intent = Intent(this, PantallaLogin::class.java)
            startActivity(intent)
        }
    }
}