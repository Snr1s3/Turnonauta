package proj.tcg.turnonauta.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.adapters.AdapterDetallTorneig
import proj.tcg.turnonauta.models.Torneig
import proj.tcg.turnonauta.models.UsuarisAmbPunts
import proj.tcg.turnonauta.models.DetallTorneig
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException

class DetallTorneig : AppCompatActivity() {
    private lateinit var tornarButton : Button
    private lateinit var response: List<UsuarisAmbPunts>
    private lateinit var response2: Torneig
    private var torneigId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detall_torneig)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        val appInstance = AppTurnonauta.getInstance()
        torneigId = appInstance.getTorneigIdApp()
        getTorneigId()
        getTornejosList()
        tornarButton = findViewById(R.id.tornar)
        tornarButton.setOnClickListener {
            val intent = Intent(this, LlistaTornejosJugats::class.java)
            startActivity(intent)
        }
    }

    private fun getTorneigId(){
        lifecycleScope.launch {
            try {
                response2 = ConnexioAPI.api().getTournamentById(torneigId)
                Log.d("Id Torneig", "ID: $response2")
                setTorneig()
            } catch (e: HttpException) {
                Toast.makeText(this@DetallTorneig, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@DetallTorneig,"Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@DetallTorneig,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTorneig(){
        val nom = findViewById<TextView>(R.id.nom)
        val codi = findViewById<TextView>(R.id.codi)
        val format= findViewById<TextView>(R.id.format)
        val joc = findViewById<TextView>(R.id.joc)
        val jugadors = findViewById<TextView>(R.id.jugadors)
        nom.text = response2.nom
        codi.text ="Codi: "+response2.idTorneig.toString()
        format.text ="Format: "+response2.format
        joc.text  = "Joc: "+response2.joc
        jugadors.text  ="Jugadors: "+ response2.numJugadors.toString()

    }

    private fun getTornejosList(){
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getUsersTournament(torneigId)
                Log.d("Id Torneig", "ID: $response")
                startRecycled()
            } catch (e: HttpException) {
                Toast.makeText(this@DetallTorneig, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@DetallTorneig,"Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@DetallTorneig,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRecycled(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        val data = ArrayList<DetallTorneig>()
        var num = 0
        for (t in response) {
            num += 1
            data.add(DetallTorneig(num.toString() +"º  "+t.username+ "   "+t.punts.toString()+" punts"))
        }
        val adapter = AdapterDetallTorneig(data)
        recyclerView.adapter = adapter
    }
}