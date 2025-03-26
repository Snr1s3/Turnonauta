package proj.tcg.turnonauta.aplicacio

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
import proj.tcg.turnonauta.detall_torneig_recyled_view.Adapter_detall_torneig_recyled_view
import proj.tcg.turnonauta.detall_torneig_recyled_view.detall_torneig_recyled_view
import proj.tcg.turnonauta.models.Torneig
import proj.tcg.turnonauta.models.UsuarisAmbPunts
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import proj.tcg.turnonauta.tornejos_jugats.recycled_view.Adapter_tornejosJugats_recyled_view
import proj.tcg.turnonauta.tornejos_jugats.recycled_view.tornejosJugats_recyled_view
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
                response2 = ConnexioAPI.API().getTournamentById(torneigId)
                Log.d("Id Torneig", "ID: "+response2)
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

    private fun setTorneig(){
        var nom = findViewById<TextView>(R.id.nom)
        var codi = findViewById<TextView>(R.id.codi)
        var format= findViewById<TextView>(R.id.format)
        var joc = findViewById<TextView>(R.id.joc)
        var jugadors = findViewById<TextView>(R.id.jugadors)
        nom.setText(response2.nom)
        codi.setText("Codi: "+response2.idTorneig.toString())
        format.setText("Format: "+response2.format)
        joc.setText("Joc: "+response2.joc)
        jugadors.setText("Jugadors: "+ response2.numJugadors.toString())

    }

    private fun getTornejosList(){
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.API().get_users_in_tournament(torneigId)
                Log.d("Id Torneig", "ID: "+response)
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
        val data = ArrayList<detall_torneig_recyled_view>()
        var num = 0
        for (t in response) {
            num = num + 1
            data.add(detall_torneig_recyled_view(num.toString() +"º  "+t.username+ "   "+t.punts.toString()+" punts"))
        }
        val adapter = Adapter_detall_torneig_recyled_view(this, data)
        recyclerView.adapter = adapter
    }
}