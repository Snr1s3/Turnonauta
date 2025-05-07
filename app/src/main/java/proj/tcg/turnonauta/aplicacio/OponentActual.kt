package proj.tcg.turnonauta.aplicacio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class OponentActual : AppCompatActivity() {
    private lateinit var btn: Button
    private var rondes: Int = 0
    private lateinit var tronda: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_oponent_actual)
        btn = findViewById(R.id.bActualitzar)
        tronda = findViewById(R.id.round)

        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        val appInstance = AppTurnonauta.getInstance()
        rondes = appInstance.getNumRonda()
        tronda.text = "Ronda " + rondes.toString()

        btn.setOnClickListener {
            appInstance.setNumRonda()
        }
    }
}