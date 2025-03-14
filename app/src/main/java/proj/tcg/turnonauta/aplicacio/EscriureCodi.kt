package proj.tcg.turnonauta.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R

import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class EscriureCodi : AppCompatActivity() {

    private lateinit var bTorneig : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escriure_codi)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        bTorneig = findViewById(R.id.bTorneig)

        bTorneig.setOnClickListener {
            val intent = Intent(this, PreviewTorneig::class.java)
            startActivity(intent)
        }
    }


}