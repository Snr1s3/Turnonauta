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
import proj.tcg.turnonauta.models.TornejosJugats
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import proj.tcg.turnonauta.adapters.AdapterTornejosJugats
import retrofit2.HttpException
import java.io.IOException

class LlistaTornejosJugats : AppCompatActivity() {
    private lateinit var filtresButton: Button
    private lateinit var response: List<Torneig>
    private var filters: Array<Int>? = arrayOf(0, 0, 0)
    private var userId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_llista_tornejos_jugats)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        val appInstance = AppTurnonauta.getInstance()
        userId = appInstance.getUserIdApp()
        val f = intent.extras?.getIntArray("filters")  // No need to convert to TypedArray
        if (f != null) {
            filters = f.toTypedArray()
        }
        getTornejosList()
        filtresButton = findViewById(R.id.filtres)
        filtresButton.setOnClickListener {
            val intent = Intent(this, FiltresTornejos::class.java)
            startActivity(intent)
        }
    }

    private fun getTornejosList() {
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getTournamentsPlayed(userId)
                Log.d("User_ID Pantalla d'Inici:", "ID: $response")
                startRecycled()
            } catch (e: HttpException) {
                Toast.makeText(
                    this@LlistaTornejosJugats,
                    "HTTP Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: IOException) {
                Toast.makeText(
                    this@LlistaTornejosJugats,
                    "Network Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(this@LlistaTornejosJugats, "Error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun startRecycled() {
        val joc = filters?.get(0) // 0, 1, 2, or 3
        val format = filters?.get(1) // 0, 1, 2, or 3
        val jugadors = filters?.get(2) // 0, 1, 2, or 3

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        val data = ArrayList<TornejosJugats>()

        // Convert the filters to corresponding string values
        val formatText = when (format) {
            1 -> "Elimin"
            2 -> "Swiss"
            else -> "" // No filter
        }
        val jocText = when (joc) {
            1 -> "MTG"
            2 -> "PKM"
            else -> "" // No filter
        }
        val jugadorsText = when (jugadors) {
            1 -> "8" // Converted to string for comparison
            2 -> "16" // Converted to string for comparison
            else -> "" // No filter
        }

        // Iterate over the tournaments and filter them based on the values
        for (t in response) {
            // Debugging logs
            Log.d("Filtres", "Filtres: jocText=$jocText  joc=$joc  t.joc=${t.joc}")
            Log.d(
                "Filtres",
                "Filtres: formatText=$formatText  format=$format  t.format=${t.format}"
            )
            Log.d(
                "Filtres",
                "Filtres: jugadorsText=$jugadorsText  jugadors=$jugadors  t.numJugadors=${t.numJugadors}"
            )

            // Compare strings directly
            if ((joc == 0 || jocText == t.joc) &&
                (format == 0 || formatText == t.format) &&
                (jugadors == 0 || jugadorsText == t.numJugadors.toString())
            ) {
                data.add(TornejosJugats(t.nom, t.idTorneig!!, t.joc, t.format!!, t.numJugadors!!))
            }
        }

        // Set the data in the RecyclerView adapter
        val adapter = AdapterTornejosJugats(this, data)
        recyclerView.adapter = adapter
    }
}