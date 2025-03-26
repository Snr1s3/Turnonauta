package proj.tcg.turnonauta.config

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import java.io.IOException

class EditarPerfil : AppCompatActivity() {
    private lateinit var textNombreUsuario: TextView
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        textNombreUsuario = findViewById(R.id.btnChangeName) // El TextView donde mostraremos el nombre

        // Recibir el ID del usuario desde el Intent
        userId = intent.getIntExtra("USER_ID", -1)

        if (userId != -1) {
          //  obtenerNombreUsuario(userId)
        }
    }

    private fun obtenerNombreUsuario(userId: Int) {
     /*   lifecycleScope.launch {
            try {
                val response = ConnexioAPI.API().getUserById(userId) // Llamar a la API

                if (response.isSuccessful) {
                    val jsonResponse = response.body()?.string()
                    val jsonObject = JSONObject(jsonResponse)
                    val nombre = jsonObject.getString("nombre") // Extraer el nombre

                    textNombreUsuario.text = nombre // Mostrar el nombre en el TextView
                } else {
                    textNombreUsuario.text = "Usuario no encontrado"
                }
            } catch (e: IOException) {
                textNombreUsuario.text = "Error de conexión"
            } catch (e: Exception) {
                textNombreUsuario.text = "Error: ${e.message}"
            }
        }

      */
    }
}
