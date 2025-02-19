package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R

class EscriureCodiMail : AppCompatActivity() {
    private lateinit var bComprobar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escriure_codi_mail)

        bComprobar = findViewById(R.id.bComprobar)

        bComprobar.setOnClickListener {
            val intent = Intent(this, NovaContrasenya::class.java)
            startActivity(intent)
        }
    }
}