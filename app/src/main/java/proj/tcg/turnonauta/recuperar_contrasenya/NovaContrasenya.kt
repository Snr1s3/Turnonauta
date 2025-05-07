package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
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


class NovaContrasenya : AppCompatActivity() {

    private lateinit var bActualitzar: Button
    private lateinit var iContra: EditText
    private lateinit var iContra2: EditText
    private lateinit var tConObl: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nova_contrasenya)
        // Inicialización de vistas
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        bActualitzar = findViewById(R.id.bActualitzar)
        iContra = findViewById(R.id.iContra)
        iContra2 = findViewById(R.id.iContra2)
        tConObl = findViewById(R.id.tConObl)



        val email = intent.getStringExtra("email") ?: ""
        Toast.makeText(this, "Correu introduït: $email", Toast.LENGTH_SHORT).show()


        bActualitzar.setOnClickListener {
            val password = iContra.text.toString()
            val confirmPassword = iContra2.text.toString()

            val errorMessage = validatePassword(password, confirmPassword)

            if (errorMessage == null) {
                val email = intent.getStringExtra("email") ?: ""
                val request = PasswordUpdateRequest(email, password)

                lifecycleScope.launch {
                    try {
                        val response = ConnexioAPI.api().updatePassword(request)
                        if (response) {
                            val intent = Intent(this@NovaContrasenya, ContrasenyaActualitzada::class.java)
                            startActivity(intent)
                        } else {
                            tConObl.text = "Error al actualitzar la contrasenya"
                            tConObl.visibility = TextView.VISIBLE
                        }
                    } catch (e: Exception) {
                        tConObl.text = "Error de connexió: ${e.message}"
                        tConObl.visibility = TextView.VISIBLE
                    }
                }
            } else {
                // Si hay errores de validación, mostrar mensaje
                tConObl.text = errorMessage
                tConObl.visibility = TextView.VISIBLE
            }


        }
    }

    // Función para validar la contraseña
    private fun validatePassword(password: String, confirmPassword: String): String? {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")

        return when {
            password.isEmpty() || confirmPassword.isEmpty() -> "Els camps no poden estar buits"
            password != confirmPassword -> getString(R.string.error_new_pw_1)
            password.length < 8 -> getString(R.string.error_new_pw_3)
            !regex.matches(password) -> getString(R.string.error_new_pw_2)
            else -> null // No hay errores
        }
    }
}
