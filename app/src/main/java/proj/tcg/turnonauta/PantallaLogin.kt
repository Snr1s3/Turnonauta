package proj.tcg.turnonauta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.aplicacio.PaginaPrincipal
import proj.tcg.turnonauta.recuperar_contrasenya.RecuperarContrasenya
import proj.tcg.turnonauta.registre.Registre

class PantallaLogin : AppCompatActivity() {
    private lateinit var bInici : Button
    private lateinit var tConObl : TextView
    private lateinit var tRegistre : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pantalla_login)

        var count = 0

        bInici = findViewById(R.id.bInici)
        tConObl = findViewById(R.id.tConObl)
        tRegistre = findViewById(R.id.tRegistre)

        bInici.setOnClickListener {
            if(count == 0){
                tConObl.visibility = View.VISIBLE
            }
            else{
                val intent = Intent(this, PaginaPrincipal::class.java)
                startActivity(intent)
            }
            count++
        }
        tConObl.setOnClickListener{
            val intent = Intent(this, RecuperarContrasenya::class.java)
            startActivity(intent)
        }
        tRegistre.setOnClickListener{
            val intent = Intent(this, Registre::class.java)
            startActivity(intent)
        }
    }
}