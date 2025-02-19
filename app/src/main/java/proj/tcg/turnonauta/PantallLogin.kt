package proj.tcg.turnonauta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.recuperar_contrasenya.RecuperarContrasenya

class PantallLogin : AppCompatActivity() {
    private lateinit var bInici : Button
    private lateinit var tConObl : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pantalla_login)

        var count = 0

        bInici = findViewById(R.id.bInici)
        tConObl = findViewById(R.id.tConObl)

        bInici.setOnClickListener {
            if(count == 0){
                tConObl.visibility = View.VISIBLE
            }
            count = count +1
        }
        tConObl.setOnClickListener{
            val intent = Intent(this, RecuperarContrasenya::class.java)
            startActivity(intent)
        }
    }
}