package proj.tcg.turnonauta.config

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R
import java.util.Locale

class Configuracio : AppCompatActivity() {
    private lateinit var botonImagen: ImageButton
    private lateinit var botonPerfil: MaterialButton
    private lateinit var botonLogOut: MaterialButton
    private lateinit var botonModo: ImageButton
    private lateinit var botonIdioma: MaterialButton
    private lateinit var botoPoliticas: MaterialButton
    private lateinit var botoTerms: MaterialButton
    private lateinit var botoUbi: ImageButton

    private lateinit var prefs2: SharedPreferences

    private var isRedBorder = false
    private var isNight = false
    private var isUbiON = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar el modo noche según la preferencia guardada
        val prefs = getSharedPreferences("ajustes", MODE_PRIVATE)
        isNight = prefs.getBoolean("modo_noche", false)
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        // Inicializar botones
        botonImagen = findViewById(R.id.btnNoti)
        botonIdioma = findViewById(R.id.btnIdioma)
        botonPerfil = findViewById(R.id.btnPerfil)
        botonLogOut = findViewById(R.id.btnLogOut)
        botonModo = findViewById(R.id.btnModo)
        botoPoliticas = findViewById(R.id.btnPoliticas)
        botoTerms = findViewById(R.id.btnTerms)
        botoUbi = findViewById(R.id.btn_ubi)

        // Configuración visual inicial
        botonImagen.setImageResource(R.drawable.campana_noti_off)
        botonImagen.setBackgroundResource(R.drawable.rounded_red_border_button)

        if (isNight) {
            botonModo.setImageResource(R.drawable.luna_turno)
            botonModo.setBackgroundResource(R.drawable.rounded_night_button)
        } else {
            botonModo.setImageResource(R.drawable.sol_turno)
            botonModo.setBackgroundResource(R.drawable.rounded_day_button)
        }

        botoUbi.setImageResource(R.drawable.location_off)
        botoUbi.setBackgroundResource(R.drawable.rounded_red_border_button)

        // Botón de modo noche
        botonModo.setOnClickListener {
            isNight = !isNight
            prefs.edit().putBoolean("modo_noche", isNight).apply()

            if (isNight) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            recreate() // Recarga la actividad para aplicar el cambio de tema
        }
        botonIdioma.setOnClickListener {
            val currentLang = prefs.getString("app_language", "") ?: ""

            val newLang = when (currentLang) {
                "es-rES" -> "en-rGB"
                "en-rGB" -> ""
                else -> "es-rES"
            }

            prefs.edit().putString("app_language", newLang).apply()
            recreateApp()
        }
        // Botón de notificaciones
        botonImagen.setOnClickListener {
            if (isRedBorder) {
                botonImagen.setImageResource(R.drawable.campana_noti_off)
                botonImagen.setBackgroundResource(R.drawable.rounded_red_border_button)
            } else {
                botonImagen.setImageResource(R.drawable.campana_noti_on)
                botonImagen.setBackgroundResource(R.drawable.rounded_green_border_button)
            }
            isRedBorder = !isRedBorder
        }

        // Botón de ubicación
        botoUbi.setOnClickListener {
            if (isUbiON) {
                botoUbi.setImageResource(R.drawable.location_off)
                botoUbi.setBackgroundResource(R.drawable.rounded_red_border_button)
            } else {
                botoUbi.setImageResource(R.drawable.location_on)
                botoUbi.setBackgroundResource(R.drawable.rounded_green_border_button)
            }
            isUbiON = !isUbiON
        }

        // Navegaciones
        botonPerfil.setOnClickListener {
            startActivity(Intent(this, EditarPerfil::class.java))
        }

        botonLogOut.setOnClickListener {
            prefs2 = getSharedPreferences("turnonauta_app", MODE_PRIVATE)
            prefs2.edit().remove("userId").apply()
            startActivity(Intent(this, PantallaLogin::class.java))
        }

        botoTerms.setOnClickListener {
            startActivity(Intent(this, TermsOfUse::class.java))
        }

        botoPoliticas.setOnClickListener {
            startActivity(Intent(this, PoliticasPrivacidad::class.java))
        }
    }

    private fun recreateApp() {
        val intent = Intent(this, Configuracio::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            val prefs = it.getSharedPreferences("ajustes", MODE_PRIVATE)
            val lang = prefs.getString("app_language", "en") ?: "en"
            val context = setLocale(it, lang)
            super.attachBaseContext(context)
        } ?: super.attachBaseContext(newBase)
    }
    fun setLocale(context: Context, language: String): Context {
        val localeParts = language.split("-r")
        val locale = if (localeParts.size == 2) Locale(localeParts[0], localeParts[1]) else Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}