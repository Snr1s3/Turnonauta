package proj.tcg.turnonauta.registre

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R
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

        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response = ConnexioAPI.API().registerUser(username, email, phone, password)
                if (response.isSuccessful) {
                    Toast.makeText(this@Registre, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Registre, PantallaLogin::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Registre, "Error en el registro", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@Registre, "Error HTTP: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("Registre", "HTTP Exception: ${e.message}")
            } catch (e: IOException) {
                Toast.makeText(this@Registre, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("Registre", "IO Exception: ${e.message}")
            } catch (e: Exception) {
                Toast.makeText(this@Registre, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("Registre", "Exception: ${e.message}")
            }
        }
    }
}
