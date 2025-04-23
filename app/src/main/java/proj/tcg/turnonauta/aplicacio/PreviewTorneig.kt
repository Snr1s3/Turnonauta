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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.models.UsuariTorneig
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import proj.tcg.turnonauta.adapters.AdapterUsuarTorneig
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.models.Torneig
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.socket.clientSocket
import retrofit2.HttpException
import java.io.IOException

class PreviewTorneig : AppCompatActivity() {
    private lateinit var btn: Button
    private var torneig_id: Int = 0
    private lateinit var torneig_t: TextView
    private lateinit var torneig_sub: TextView
    private lateinit var torneig: Torneig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preview_torneig)

        btn = findViewById(R.id.bActualitzar)
        torneig_t = findViewById(R.id.torneig_t)
        torneig_sub = findViewById(R.id.torneig_sub)

        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        val appInstance = AppTurnonauta.getInstance()
        torneig_id = appInstance.getTorneigIdApp()

        torneig_t.text = "Torneig $torneig_id"
        getTorneigId()

        val client = clientSocket()
        lifecycleScope.launch(Dispatchers.IO) {
            client.main(this@PreviewTorneig) { responseParts ->
                runOnUiThread {
                    startRecycled(responseParts)
                }
            }
        }

        btn.setOnClickListener {
            val intent = Intent(this, OponentActual::class.java)
            startActivity(intent)
        }
    }

    private fun getTorneigId() {
        lifecycleScope.launch {
            try {
                torneig = ConnexioAPI.api().getTournamentById(torneig_id)
                torneig_sub.text = "${torneig.nom} ${torneig.joc} ${torneig.numJugadors} Jugadors"
            } catch (e: HttpException) {
                Toast.makeText(this@PreviewTorneig, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@PreviewTorneig, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@PreviewTorneig, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startRecycled(list: List<String>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val data = ArrayList<UsuariTorneig>()

        for (i in 1 until list.size) {
            if (list[i] != "1") {
                data.add(UsuariTorneig(R.drawable.logo2, list[i]))
            }
        }

        val adapter = AdapterUsuarTorneig(data)
        recyclerView.adapter = adapter
    }
}
