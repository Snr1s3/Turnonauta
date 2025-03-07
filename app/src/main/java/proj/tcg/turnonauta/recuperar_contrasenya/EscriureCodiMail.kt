package proj.tcg.turnonauta.recuperar_contrasenya

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.screen.MenuInferiorAndroid

class EscriureCodiMail : AppCompatActivity() {

    private lateinit var bComprobar: Button
    private lateinit var otpFields: List<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escriure_codi_mail)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        bComprobar = findViewById(R.id.bComprobar)

        // Obtener referencias a los EditText de los códigos
        otpFields = listOf(
            findViewById(R.id.otp1),
            findViewById(R.id.otp2),
            findViewById(R.id.otp3),
            findViewById(R.id.otp4),
            findViewById(R.id.otp5)
        )

        setupOtpInputs()

        bComprobar.setOnClickListener {
            val enteredCode = getOtpCode()
            val correctCode = 12345  // Aquí pondrías el código correcto recibido por correo

            if (enteredCode == correctCode) {
                val intent = Intent(this, NovaContrasenya::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Código incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupOtpInputs() {
        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (index < otpFields.size - 1) {
                            otpFields[index + 1].requestFocus() // Mueve el cursor al siguiente
                        }
                    } else if (s?.isEmpty() == true && index > 0) {
                        otpFields[index - 1].requestFocus() // Retrocede si se borra
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun getOtpCode(): Int {
        val otpString = otpFields.joinToString("") { it.text.toString() }
        return otpString.toIntOrNull() ?: 0  // Convierte a Int o devuelve 0 si está vacío
    }
}
