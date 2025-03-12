package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R

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
        bActualitzar = findViewById(R.id.bActualitzar)
        iContra = findViewById(R.id.iContra)
        iContra2 = findViewById(R.id.iContra2)
        tConObl = findViewById(R.id.tConObl)

        bActualitzar.setOnClickListener {
            val password = iContra.text.toString()
            val confirmPassword = iContra2.text.toString()

            val errorMessage = validatePassword(password, confirmPassword)

            if (errorMessage == null) {
                // Si no hay errores, avanzar a la siguiente pantalla
                val intent = Intent(this, ContrasenyaActualitzada::class.java)
                startActivity(intent)
            } else {
                // Si hay un error, mostrarlo en el TextView
                tConObl.text = errorMessage
                tConObl.visibility = TextView.VISIBLE
            }
        }
    }

    // Función para validar la contraseña
    private fun validatePassword(password: String, confirmPassword: String): String? {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")

        return when {
            password != confirmPassword -> getString(R.string.error_new_pw_1)
            password.length < 8 -> getString(R.string.error_new_pw_3)
            !regex.matches(password) -> getString(R.string.error_new_pw_2)
            else -> null // No hay errores
        }
    }
}
