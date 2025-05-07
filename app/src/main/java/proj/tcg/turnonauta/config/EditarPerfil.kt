package proj.tcg.turnonauta.config

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.models.OnNameUpdatedListener
import proj.tcg.turnonauta.models.UsuarisStatistics
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import retrofit2.HttpException
import java.io.IOException

class EditarPerfil : AppCompatActivity(), OnNameUpdatedListener {
    private lateinit var textNombreUsuario: TextView
    private var userId: Int = 0
    private lateinit var response: UsuarisStatistics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        textNombreUsuario = findViewById(R.id.btnChangeName)

        val appInstance = AppTurnonauta.getInstance()
        userId = appInstance.getUserIdApp()

        getDataUser()

        textNombreUsuario.setOnClickListener {
            val fragment = EditName_Fragment()
            val fragmentManager: FragmentManager = supportFragmentManager
            fragment.show(fragmentManager, "editar_nombre_fragment")
        }

        val btnBorrarPerfil = findViewById<Button>(R.id.btnLogOut)
        btnBorrarPerfil.setOnClickListener {
            showDeleteConfirmationDialog() // Mostrar confirmación antes de eliminar
        }
    }

    override fun onNameUpdated(newName: String) {
        val nomText = findViewById<MaterialButton>(R.id.btnChangeName)
        nomText.text = newName
    }

    private fun getDataUser() {
        lifecycleScope.launch {
            try {
                // Obtener las estadísticas del usuario
                response = ConnexioAPI.api().getStatistic(userId)
                Log.d("User_ID Pantalla d'Inici:", "ID: " + response)
                val nomText = findViewById<MaterialButton>(R.id.btnChangeName)
                nomText.setText(response.username.toString()) // Establecer el nombre de usuario
            } catch (e: HttpException) {
                Toast.makeText(this@EditarPerfil, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@EditarPerfil, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@EditarPerfil, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Estás seguro?")
        builder.setMessage("Estás a punto de eliminar tu perfil. Esta acción no se puede deshacer.")
        builder.setPositiveButton("Sí") { dialog, _ ->
            deleteUser() // Eliminar el perfil si el usuario confirma
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Cerrar el diálogo si el usuario cancela
        }
        builder.show()
    }

    private fun deleteUser() {
        lifecycleScope.launch {
            try {
                val success = ConnexioAPI.api().deleteUsers(userId)

                if (success) {
                    Toast.makeText(this@EditarPerfil, "Perfil eliminado correctamente.", Toast.LENGTH_SHORT).show()
                    finish() // Cerrar la actividad y redirigir al login o pantalla principal
                } else {
                    Toast.makeText(this@EditarPerfil, "Error al eliminar el perfil.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@EditarPerfil, "Error del servidor: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@EditarPerfil, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@EditarPerfil, "Error inesperado: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
