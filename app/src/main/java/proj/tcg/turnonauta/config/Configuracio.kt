package proj.tcg.turnonauta.config

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R

class Configuracio : AppCompatActivity() {
    private lateinit var botonImagen: ImageButton
    private lateinit var botonPerfil: MaterialButton
    private lateinit var botonLogOut: MaterialButton
    private lateinit var botonModo: ImageButton
    private lateinit var botoPoliticas: MaterialButton
    private lateinit var botoTerms: MaterialButton
    private lateinit var botoUbi: ImageButton

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
        botonPerfil = findViewById(R.id.btnPerfil)
        botonLogOut = findViewById(R.id.btnLogOut)
        botonModo = findViewById(R.id.btnModo)
        botoPoliticas = findViewById(R.id.btnPoliticas)
        botoTerms = findViewById(R.id.btnTerms)
        botoUbi = findViewById(R.id.btn_ubi)

        // Configuración visual inicial
        botonImagen.setImageResource(R.drawable.campana_noti_on)
        botonImagen.setBackgroundResource(R.drawable.rounded_green_border_button)

        if (isNight) {
            botonModo.setImageResource(R.drawable.luna_turno)
            botonModo.setBackgroundResource(R.drawable.rounded_night_button)
        } else {
            botonModo.setImageResource(R.drawable.sol_turno)
            botonModo.setBackgroundResource(R.drawable.rounded_day_button)
        }

        botoUbi.setImageResource(R.drawable.location_on)
        botoUbi.setBackgroundResource(R.drawable.rounded_green_border_button)

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

        // Botón de notificaciones
        botonImagen.setOnClickListener {
            if (isRedBorder) {
                botonImagen.setImageResource(R.drawable.campana_noti_on)
                botonImagen.setBackgroundResource(R.drawable.rounded_green_border_button)
            } else {
                botonImagen.setImageResource(R.drawable.campana_noti_off)
                botonImagen.setBackgroundResource(R.drawable.rounded_red_border_button)
            }
            isRedBorder = !isRedBorder
        }

        // Botón de ubicación
        botoUbi.setOnClickListener {
            if (isUbiON) {
                botoUbi.setImageResource(R.drawable.location_on)
                botoUbi.setBackgroundResource(R.drawable.rounded_green_border_button)
            } else {
                botoUbi.setImageResource(R.drawable.location_off)
                botoUbi.setBackgroundResource(R.drawable.rounded_red_border_button)
            }
            isUbiON = !isUbiON
        }

        // Navegaciones
        botonPerfil.setOnClickListener {
            startActivity(Intent(this, EditarPerfil::class.java))
        }

        botonLogOut.setOnClickListener {
            startActivity(Intent(this, PantallaLogin::class.java))
        }

        botoTerms.setOnClickListener {
            startActivity(Intent(this, TermsOfUse::class.java))
        }

        botoPoliticas.setOnClickListener {
            startActivity(Intent(this, PoliticasPrivacidad::class.java))
        }
    }
}
