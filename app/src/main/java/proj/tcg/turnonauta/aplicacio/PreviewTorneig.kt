package proj.tcg.turnonauta.aplicacio

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
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
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class PreviewTorneig : AppCompatActivity() {
    private var torneig_id: Int = 0
    private lateinit var torneig_t: TextView
    private lateinit var torneig_sub: TextView
    private lateinit var torneig: Torneig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preview_torneig)

        torneig_t = findViewById(R.id.torneig_t)
        torneig_sub = findViewById(R.id.torneig_sub)

        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        val appInstance = AppTurnonauta.getInstance()
        torneig_id = appInstance.getTorneigIdApp()

        torneig_t.text = "Torneig $torneig_id"
        getTorneigId()

        val client = appInstance.getSocket()
        val torneigId = appInstance.getTorneigIdApp()
        val userId = appInstance.getUserIdApp()
        val playerName = appInstance.getPlayerNameApp()

        lifecycleScope.launch(Dispatchers.IO) {
            client.connect(applicationContext,playerName,torneigId,userId) { responseParts ->
                runOnUiThread {
                    startRecycled(responseParts)
                }
            }
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
        if(list[0] == "2"){
            val intent = Intent(this, OponentActual::class.java)
            startActivity(intent)
        }
        else if(list[0] == "3"){
            val intent = Intent(this, DetallTorneig::class.java)
            startActivity(intent)
        }
        for (i in 1 until list.size) {
            if (list[i] != "1") {
                data.add(UsuariTorneig(R.drawable.logo2, list[i]))
            }
        }

        val adapter = AdapterUsuarTorneig(data)
        recyclerView.adapter = adapter
    }override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            val prefs = it.getSharedPreferences("ajustes", MODE_PRIVATE)
            val lang = prefs.getString("app_language", "en") ?: "en"
            val context = setLocale(it, lang)
            super.attachBaseContext(context)
        } ?: super.attachBaseContext(newBase)
    }

    fun setLocale(context: Context, language: String): Context {
        val localeParts = language.split("-r")
        val locale =
            if (localeParts.size == 2) Locale(localeParts[0], localeParts[1]) else Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
