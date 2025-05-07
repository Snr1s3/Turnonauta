package proj.tcg.turnonauta.aplicacio

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.models.EmparellamentNom
import proj.tcg.turnonauta.models.Ronda
import proj.tcg.turnonauta.models.UpdateRondaRequest
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException

class OponentActual : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var emparellament: EmparellamentNom
    private var rondes: Int = 0
    private var numJugador: Int = 0
    private var idOponent: Int = 0
    private var numOponent: Int = 0
    private var guanyador: Int = 0
    private var perdedor: Int = 0
    private lateinit var ronda : Ronda
    private val appInstance = AppTurnonauta.getInstance()
    private lateinit var tronda: TextView
    private lateinit var toponent: TextView
    private lateinit var playername: TextView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_oponent_actual)

        btn = findViewById(R.id.bActualitzar)
        tronda = findViewById(R.id.round)
        toponent = findViewById(R.id.oponent)
        playername = findViewById(R.id.playerName)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        getPairing()

        btn.setOnClickListener {
            val radioGroup1 = findViewById<RadioGroup>(R.id.Partida1)
            val selectedId1 = radioGroup1.checkedRadioButtonId
            val radioGroup2 = findViewById<RadioGroup>(R.id.Partida2)
            val selectedId2 = radioGroup2.checkedRadioButtonId
            val radioGroup3 = findViewById<RadioGroup>(R.id.Partida3)
            val selectedId3 = radioGroup3.checkedRadioButtonId

            var puntsJ0 = 0
            var puntsJ2 = 0

            if (selectedId1 != -1 && selectedId2 != -1 && selectedId3 != -1) {
                val selectedIndex1 = radioGroup1.indexOfChild(findViewById(selectedId1))
                val selectedIndex2 = radioGroup2.indexOfChild(findViewById(selectedId2))
                val selectedIndex3 = radioGroup3.indexOfChild(findViewById(selectedId3))

                if (selectedIndex1 == 0) puntsJ0++ else puntsJ2++
                if (selectedIndex2 == 0) puntsJ0++ else puntsJ2++
                if (selectedIndex3 == 0) puntsJ0++ else puntsJ2++
                if(puntsJ2 == 3){
                    puntsJ2 = 2
                }
                if(puntsJ0 == 3){
                    puntsJ0 = 2
                }
                Toast.makeText(this@OponentActual, "Punts J1 $puntsJ0", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@OponentActual, "Punts J2 $puntsJ2", Toast.LENGTH_SHORT).show()
                var idRonda = 0
                var idJ1 = 0
                var pj1 = 0
                var idJ2 = 0
                var pj2 = 0
                if(numJugador == 1 ){
                    if(puntsJ0 > puntsJ2){
                        guanyador = emparellament.id_usuari1
                        perdedor = emparellament.id_usuari2
                    }
                    if(puntsJ0 < puntsJ2){
                        guanyador = emparellament.id_usuari2
                        perdedor = emparellament.id_usuari1
                    }
                    idRonda = emparellament.id_ronda
                    idJ1 = emparellament.id_usuari1
                    idJ2 = emparellament.id_usuari2
                    pj1 = puntsJ0
                    pj2 = puntsJ2

                }
                else if(numJugador == 2){
                    if(puntsJ0 > puntsJ2){
                        guanyador = emparellament.id_usuari2
                        perdedor = emparellament.id_usuari1
                    }
                    if(puntsJ0 < puntsJ2){
                        guanyador = emparellament.id_usuari1
                        perdedor = emparellament.id_usuari2
                    }
                    idRonda = emparellament.id_ronda
                    idJ1 = emparellament.id_usuari2
                    idJ2 = emparellament.id_usuari1
                    pj1 = puntsJ2
                    pj2 = puntsJ0
                }
                val updataRonda = UpdateRondaRequest(idRonda,idJ1,pj1,idJ2,pj2,guanyador,perdedor)


                lifecycleScope.launch {
                    try {
                        ronda = ConnexioAPI.api().updateRonda(updataRonda)
                    } catch (e: HttpException) {
                        Toast.makeText(this@OponentActual, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    } catch (e: IOException) {
                        Toast.makeText(this@OponentActual, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@OponentActual, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                progressBar.visibility = View.VISIBLE
            } else {
                if (selectedId1 == -1) {
                    Toast.makeText(this@OponentActual, "Error Fila 1", Toast.LENGTH_SHORT).show()
                } else if (selectedId2 == -1) {
                    Toast.makeText(this@OponentActual, "Error Fila 2", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@OponentActual, "Error Fila 3", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getPairing() {
        lifecycleScope.launch {
            try {
                emparellament = ConnexioAPI.api().getPairing(
                    appInstance.getTorneigIdApp(),
                    appInstance.getUserIdApp()
                )

                if (appInstance.getPlayerNameApp() == emparellament.nom_usuari1) {
                    toponent.text = emparellament.nom_usuari2
                    playername.text = emparellament.nom_usuari2
                    numJugador = 1
                    numOponent = 2
                    idOponent = emparellament.id_usuari2
                } else {
                    toponent.text = emparellament.nom_usuari1
                    playername.text = emparellament.nom_usuari1
                    numJugador = 2
                    numOponent = 1
                    idOponent = emparellament.id_usuari1
                }
                rondes = appInstance.getNumRonda()
                appInstance.setNumRonda()
                tronda.text = "Ronda $rondes"

            } catch (e: HttpException) {
                Toast.makeText(this@OponentActual, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@OponentActual, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@OponentActual, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}