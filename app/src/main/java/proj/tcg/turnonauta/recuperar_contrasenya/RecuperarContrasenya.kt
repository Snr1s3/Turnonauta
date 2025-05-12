package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.registre.Registre
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import java.util.Locale
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.widget.Toast
import proj.tcg.turnonauta.retrofit.ConnexioAPI


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
            val correu = iCorreu.text.toString().trim()

            if (correu.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val exists = withContext(Dispatchers.IO) {
                            ConnexioAPI.api().checkEmailExists(correu)
                        }

                        if (exists) {
                            val intent = Intent(this@RecuperarContrasenya, EscriureCodiMail::class.java)
                            intent.putExtra("email", correu)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@RecuperarContrasenya, "Aquest correu no existeix", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@RecuperarContrasenya, "Error de connexió: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                iCorreu.error = "Introdueix un correu vàlid"
            }
        }



        bRegistrarse.setOnClickListener{
            val intent = Intent(this, Registre::class.java)
            startActivity(intent)
        }
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