package proj.tcg.turnonauta.aplicacio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
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
        val radioGroupJoc = findViewById<RadioGroup>(R.id.radio_joc)
        val radioGroupFormat = findViewById<RadioGroup>(R.id.radio_format)
        val radioGroupJugadors = findViewById<RadioGroup>(R.id.radio_jugadors)
        guardarButton = findViewById(R.id.guardar)
        guardarButton.setOnClickListener{
            val selectedJocId = radioGroupJoc.checkedRadioButtonId
            val selectedFormatId = radioGroupFormat.checkedRadioButtonId
            val selectedJugadorsId = radioGroupJugadors.checkedRadioButtonId
            val jocBool: Int = when (selectedJocId) {
                R.id.radio_magic -> 1
                R.id.radio_pokemon -> 2
                else -> 0
            }
            val formatBool: Int = when (selectedFormatId) {
                R.id.radio_eliminatoria -> 1
                R.id.radio_swiss -> 2
                else -> 0
            }
            val jugadorsBool:Int= when (selectedJugadorsId) {
                R.id.radio_8 -> 1
                R.id.radio_16 -> 2
                else -> 0
            }
            //Toast.makeText(this, "Joc: $jocBool\nFormat: $formatBool\nJugadors: $jugadorsBool", Toast.LENGTH_LONG).show()
            val bundle = Bundle().apply {
                putIntArray("filters", intArrayOf(jocBool, formatBool, jugadorsBool))
            }
            val intent = Intent(this, LlistaTornejosJugats::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        }
    }
}