package proj.tcg.turnonauta.aplicacio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import proj.tcg.turnonauta.tornejos_jugats.recycled_view.Adapter_tornejosJugats_recyled_view
import proj.tcg.turnonauta.tornejos_jugats.recycled_view.tornejosJugats_recyled_view

class LlistaTornejosJugats : AppCompatActivity() {
    private lateinit var filtresButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_llista_tornejos_jugats)
        startRecycled(8)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        filtresButton = findViewById(R.id.filtres)
        filtresButton.setOnClickListener {
            val intent = Intent(this, FiltresTornejos::class.java)
            startActivity(intent)
        }
    }
    private fun startRecycled(n:Int){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        val data = ArrayList<tornejosJugats_recyled_view>()
        for (i in 1..n) {
            data.add(tornejosJugats_recyled_view("Torneig $i", 23,"MTG", "Suis", 16))
        }
        val adapter = Adapter_tornejosJugats_recyled_view(this, data)
        recyclerView.adapter = adapter
    }
}