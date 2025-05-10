package proj.tcg.turnonauta.registre

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.models.NewUser
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class Registre : AppCompatActivity() {
    private lateinit var bRegistre: Button
    private lateinit var eNomUsuari: EditText
    private lateinit var eCorreu: EditText
    private lateinit var eTelf: EditText
    private lateinit var eContra: EditText
    private lateinit var eRepContra: EditText
    private lateinit var tConObl: TextView // Para mostrar errores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registre)

        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        bRegistre = findViewById(R.id.bRegistre)
        eNomUsuari = findViewById(R.id.iNomUsuari)
        eCorreu = findViewById(R.id.iCorreu)
        eTelf = findViewById(R.id.itelf)
        eContra = findViewById(R.id.iContra1)
        eRepContra = findViewById(R.id.iContra2)
        tConObl = findViewById(R.id.tConObl) // Asignamos el TextView

        bRegistre.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = eNomUsuari.text.toString().trim()
        val email = eCorreu.text.toString().trim()
        val phone = eTelf.text.toString().trim()
        val password = eContra.text.toString().trim()
        val confirmPassword = eRepContra.text.toString().trim()

        // Limpiar errores anteriores
        tConObl.text = ""
        tConObl.visibility = TextView.GONE

        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Todos los camps són obligatoris")
            return
        }

        // Validar email
        val emailError = validateEmail(email)
        if (emailError != null) {
            showError(emailError)
            return
        }

        // Lanzamos una corrutina para poder llamar a la API
        lifecycleScope.launch {
            try {
                // ⚠️ Verificamos si el username ya existe
                val usernameExists = ConnexioAPI.api().checkUsernameExists(username)
                if (usernameExists) {
                    showError("Aquest nom d'usuari ja existeix")
                    return@launch
                }

                // 🧠 Validar contraseña
                val passwordError = validatePassword(password, confirmPassword)
                if (passwordError != null) {
                    showError(passwordError)
                    return@launch
                }

                // ✅ Si todo está OK, hacemos el registro
                val newUser = NewUser(username, email, phone, password)
                val response = ConnexioAPI.api().registerUser(newUser)

                if (response != null) {
                    val intent = Intent(this@Registre, PantallaLogin::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showError("Error en el registre, torna-ho a intentar.")
                }

            } catch (e: HttpException) {
                showError("Error del servidor: ${e.message}")
                Log.e("Registre", "HTTP Exception: ${e.message}")
            } catch (e: IOException) {
                showError("Error de connexió: revisa internet.")
                Log.e("Registre", "IO Exception: ${e.message}")
            } catch (e: Exception) {
                showError("Error inesperat: ${e.message}")
                Log.e("Registre", "Exception: ${e.message}")
            }
        }
    }

    private fun validateEmail(email: String): String? {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return if (!regex.matches(email)) {
            "El correu electrònic no és vàlid"
        } else {
            null
        }
    }

    private fun validatePassword(password: String, confirmPassword: String): String? {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")

        return when {
            password != confirmPassword -> "Les contrasenyes no coincideixen"
            password.length < 8 -> "La contrasenya ha de tenir almenys 8 caràcters"
            !regex.matches(password) -> "Ha de contenir almenys una lletra, un número i un caràcter especial"
            else -> null
        }
    }

    private fun showError(message: String) {
        tConObl.text = message
        tConObl.visibility = TextView.VISIBLE
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
