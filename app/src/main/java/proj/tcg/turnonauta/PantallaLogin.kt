package proj.tcg.turnonauta
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.app.AppTurnonauta
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.aplicacio.PaginaPrincipal
import proj.tcg.turnonauta.recuperar_contrasenya.RecuperarContrasenya
import proj.tcg.turnonauta.registre.Registre
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException

class PantallaLogin : AppCompatActivity() {
    private lateinit var bInici: Button
    private lateinit var tConObl: TextView
    private lateinit var tRegistre: TextView
    private lateinit var eTUsuari: EditText
    private lateinit var eTContra: EditText
    private var response: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pantalla_login)

        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        bInici = findViewById(R.id.bInici)
        tConObl = findViewById(R.id.tConObl)
        tRegistre = findViewById(R.id.tRegistre)
        eTUsuari = findViewById(R.id.eTUsuari)
        eTContra = findViewById(R.id.eTContra)
        response = -1

        // Asegurar que el mensaje de error esté oculto al iniciar
        tConObl.visibility = View.GONE

        bInici.setOnClickListener {
            checkLogin()
        }
        tConObl.setOnClickListener {
            val intent = Intent(this, RecuperarContrasenya::class.java)
            startActivity(intent)
        }
        tRegistre.setOnClickListener {
            val intent = Intent(this, Registre::class.java)
            startActivity(intent)
        }
    }

    private fun checkLogin() {
        val username = eTUsuari.text.toString().trim()
        val password = eTContra.text.toString().trim()

        // Ocultar mensaje de error antes de intentar login
        tConObl.visibility = View.GONE

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getLogin(username, password)
                Log.d("User_ID Login:", "ID: $response")

                if (response > -1) {
                    val appInstance = AppTurnonauta.getInstance()
                    appInstance.setUserIdApp(response)

                    Toast.makeText(this@PantallaLogin, "Login Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PantallaLogin, PaginaPrincipal::class.java)
                    startActivity(intent)
                } else {
                    // Mostrar el mensaje de error si las credenciales son incorrectas
                    tConObl.visibility = View.VISIBLE
                }
            } catch (e: HttpException) {
                tConObl.visibility = View.VISIBLE
                Toast.makeText(this@PantallaLogin, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                tConObl.visibility = View.VISIBLE
                Toast.makeText(this@PantallaLogin, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                tConObl.visibility = View.VISIBLE
                Toast.makeText(this@PantallaLogin, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
