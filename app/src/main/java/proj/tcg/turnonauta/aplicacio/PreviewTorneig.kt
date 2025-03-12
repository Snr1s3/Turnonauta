package proj.tcg.turnonauta.aplicacio

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

import proj.tcg.turnonauta.usuari_recyled_view.AdapterUsuariRecycledView

import proj.tcg.turnonauta.usuari_recyled_view.usuari_recyled_view

class PreviewTorneig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preview_torneig)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        startRecycled(8)
    }
    private fun startRecycled(n:Int){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val data = ArrayList<usuari_recyled_view>()
        for (i in 1..n) {
            data.add(usuari_recyled_view(R.drawable.logo2, "Jugador $i"))
        }
        val adapter = AdapterUsuariRecycledView(data)
        recyclerView.adapter = adapter
    }
}