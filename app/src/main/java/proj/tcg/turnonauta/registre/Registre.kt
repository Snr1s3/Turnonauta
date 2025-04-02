package proj.tcg.turnonauta.registre

import android.content.Intent
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
import proj.tcg.turnonauta.models.Usuaris
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException

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

        // Limpiar mensaje de error anterior
        tConObl.text = ""
        tConObl.visibility = TextView.GONE

        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Todos los campos son obligatorios")
            return
        }

        // Validar la contraseña con los requisitos mínimos
        val passwordError = validatePassword(password, confirmPassword)
        if (passwordError != null) {
            showError(passwordError)
            return
        }

        lifecycleScope.launch {
            try {
              val newUser = NewUser(username,  email, phone, password)
                val response: Usuaris = ConnexioAPI.api().registerUser(newUser)

                if (response != null) {
                    val intent = Intent(this@Registre, PantallaLogin::class.java)
                    startActivity(intent)
                } else {
                    showError("Error en el registro, intenta nuevamente.")
                }
            } catch (e: HttpException) {
                showError("Error en el servidor: ${e.message}")
                Log.e("Registre", "HTTP Exception: ${e.message}")
            } catch (e: IOException) {
                showError("Error de conexión: Verifica tu internet.")
                Log.e("Registre", "IO Exception: ${e.message}")
            } catch (e: Exception) {
                showError("Error inesperado: ${e.message}")
                Log.e("Registre", "Exception: ${e.message}")
            }
        }
    }

    private fun validatePassword(password: String, confirmPassword: String): String? {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")

        return when {
            password != confirmPassword -> "Las contraseñas no coinciden"
            password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            !regex.matches(password) -> "Debe contener al menos una letra, un número y un carácter especial"
            else -> null
        }
    }

    private fun showError(message: String) {
        tConObl.text = message
        tConObl.visibility = TextView.VISIBLE
    }
}