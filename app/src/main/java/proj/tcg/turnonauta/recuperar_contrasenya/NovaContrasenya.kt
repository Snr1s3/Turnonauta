package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class NovaContrasenya : AppCompatActivity() {
    private lateinit var bActualitzar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nova_contrasenya)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        bActualitzar = findViewById(R.id.bActualitzar)

        bActualitzar.setOnClickListener {
            val intent = Intent(this, ContrasenyaActualitzada::class.java)
            startActivity(intent)
        }
    }
}