package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.registre.Registre
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class RecuperarContrasenya : AppCompatActivity(){
    private lateinit var bEnviar : Button
    private lateinit var bRegistrarse : TextView
    private lateinit var iCorreu : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contrasenya)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        bEnviar = findViewById(R.id.bEnviar)
        iCorreu = findViewById(R.id.iCorreu)
        bRegistrarse=findViewById(R.id.textRegistrarse)

        bEnviar.setOnClickListener {
                val intent = Intent(this, EscriureCodiMail::class.java)
            startActivity(intent)
        }

        bRegistrarse.setOnClickListener{
            val intent = Intent(this, Registre::class.java)
            startActivity(intent)
        }
    }
}