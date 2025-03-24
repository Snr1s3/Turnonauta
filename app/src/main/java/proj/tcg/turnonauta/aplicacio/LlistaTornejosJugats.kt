package proj.tcg.turnonauta.aplicacio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.models.Torneig
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import proj.tcg.turnonauta.tornejos_jugats.recycled_view.Adapter_tornejosJugats_recyled_view
import proj.tcg.turnonauta.tornejos_jugats.recycled_view.tornejosJugats_recyled_view
import retrofit2.HttpException
import java.io.IOException

class LlistaTornejosJugats : AppCompatActivity() {
    private lateinit var filtresButton : Button
    private lateinit var response: List<Torneig>
    private var userId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_llista_tornejos_jugats)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        val appInstance = AppTurnonauta.getInstance()
        userId = appInstance.getUserIdApp()
        getTornejosList()
        filtresButton = findViewById(R.id.filtres)
        filtresButton.setOnClickListener {
            val intent = Intent(this, FiltresTornejos::class.java)
            startActivity(intent)
        }
    }

    private fun getTornejosList(){
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.API().getTournamentsPlayed(userId)
                Log.d("User_ID Pantalla d'Inici:", "ID: "+response)
                startRecycled()
            } catch (e: HttpException) {
                Toast.makeText(this@LlistaTornejosJugats, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@LlistaTornejosJugats,"Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@LlistaTornejosJugats,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRecycled(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        val data = ArrayList<tornejosJugats_recyled_view>()
        for (t in response) {
            data.add(tornejosJugats_recyled_view(t.nom, t.idTorneig!!,t.joc, t.format!!, t.numJugadors!!))
        }
        val adapter = Adapter_tornejosJugats_recyled_view(this, data)
        recyclerView.adapter = adapter
    }
}