package proj.tcg.turnonauta.config

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.models.UsuarisStatistics
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import retrofit2.HttpException
import java.io.IOException

class EditarPerfil : AppCompatActivity() {
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

    }

    private fun getDataUser(){
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getStatistic(userId)
                Log.d("User_ID Pantalla d'Inici:", "ID: "+response)
                val nomText = findViewById<MaterialButton>(R.id.btnChangeName)

                nomText.setText(response.username)
            } catch (e: HttpException) {
                Toast.makeText(this@EditarPerfil, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@EditarPerfil, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@EditarPerfil,"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
