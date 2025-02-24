package proj.tcg.turnonauta.registre

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R

class Registre : AppCompatActivity() {
    private lateinit var bRegistre : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registre)

        bRegistre = findViewById(R.id.bRegistre)

        bRegistre.setOnClickListener{
            val intent = Intent(this, PantallaLogin::class.java)
            startActivity(intent)
        }
    }
}