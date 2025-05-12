package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.models.PasswordUpdateRequest
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import java.util.Locale

class NovaContrasenya : AppCompatActivity() {

    private lateinit var bActualitzar: Button
    private lateinit var iContra: EditText
    private lateinit var iContra2: EditText
    private lateinit var tConObl: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nova_contrasenya)

        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        bActualitzar = findViewById(R.id.bActualitzar)
        iContra = findViewById(R.id.iContra)
        iContra2 = findViewById(R.id.iContra2)
        tConObl = findViewById(R.id.tConObl)

        val email = intent.getStringExtra("email") ?: ""
        Toast.makeText(this, getString(R.string.toast_email_received, email), Toast.LENGTH_SHORT).show()

        bActualitzar.setOnClickListener {
            val password = iContra.text.toString()
            val confirmPassword = iContra2.text.toString()

            val errorMessage = validatePassword(password, confirmPassword)

            if (errorMessage == null) {
                val request = PasswordUpdateRequest(email, password)

                lifecycleScope.launch {
                    try {
                        val response = ConnexioAPI.api().updatePassword(request)
                        if (response) {
                            val intent = Intent(this@NovaContrasenya, ContrasenyaActualitzada::class.java)
                            startActivity(intent)
                        } else {
                            tConObl.text = getString(R.string.error_pw_update_failed)
                            tConObl.visibility = TextView.VISIBLE
                        }
                    } catch (e: Exception) {
                        tConObl.text = getString(R.string.error_connection, e.message ?: "")
                        tConObl.visibility = TextView.VISIBLE
                    }
                }
            } else {
                tConObl.text = errorMessage
                tConObl.visibility = TextView.VISIBLE
            }
        }
    }

    private fun validatePassword(password: String, confirmPassword: String): String? {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")

        return when {
            password.isEmpty() || confirmPassword.isEmpty() -> getString(R.string.error_pw_empty_fields)
            password != confirmPassword -> getString(R.string.error_new_pw_1)
            password.length < 8 -> getString(R.string.error_new_pw_3)
            !regex.matches(password) -> getString(R.string.error_new_pw_2)
            else -> null
        }
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
        val locale =
            if (localeParts.size == 2) Locale(localeParts[0], localeParts[1]) else Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
