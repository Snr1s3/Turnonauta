package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R

class RecuperarContrasenya : AppCompatActivity(){
    private lateinit var bEnviar : Button
    private lateinit var iCorreu : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contrasenya)

        bEnviar = findViewById(R.id.bEnviar)
        iCorreu = findViewById(R.id.iCorreu)

        bEnviar.setOnClickListener {
                val intent = Intent(this, EscriureCodiMail::class.java)
                startActivity(intent)
        }
    }
}