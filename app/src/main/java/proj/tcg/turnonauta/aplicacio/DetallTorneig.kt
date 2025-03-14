package proj.tcg.turnonauta.aplicacio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R

class DetallTorneig : AppCompatActivity() {
    private lateinit var tornarButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detall_torneig)
        tornarButton = findViewById(R.id.tornar)
        tornarButton.setOnClickListener {
            val intent = Intent(this, LlistaTornejosJugats::class.java)
            startActivity(intent)
        }

    }
}