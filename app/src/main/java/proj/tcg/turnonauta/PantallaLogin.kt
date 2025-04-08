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
import proj.tcg.turnonauta.socket.clientSocket
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
        val client = clientSocket()
        client.main(this)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        bInici = findViewById(R.id.bInici)
        tConObl = findViewById(R.id.tConObl)
        tRegistre = findViewById(R.id.tRegistre)
        eTUsuari = findViewById(R.id.eTUsuari)
        eTContra = findViewById(R.id.eTContra)
        response = -1

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

        tConObl.visibility = View.GONE

        if (username.isEmpty() || password.isEmpty()) {
            tConObl.text = "Debe ingresar usuario y contraseña"
            tConObl.visibility = View.VISIBLE
            return
        }

        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getLogin(username, password)
                Log.d("User_ID Login:", "ID: $response")

                if (response > -1) {
                    val appInstance = AppTurnonauta.getInstance()
                    appInstance.setUserIdApp(response)

                    val intent = Intent(this@PantallaLogin, PaginaPrincipal::class.java)
                    startActivity(intent)
                } else {
                    tConObl.text = "Usuario o contraseña incorrectos"
                    tConObl.visibility = View.VISIBLE
                }
            } catch (e: HttpException) {
                tConObl.text = "Error del servidor, intente más tarde"
                tConObl.visibility = View.VISIBLE
            } catch (e: IOException) {
                tConObl.text = "Problema de conexión, verifique su internet"
                tConObl.visibility = View.VISIBLE
            } catch (e: Exception) {
                tConObl.text = "Ocurrió un error inesperado"
                tConObl.visibility = View.VISIBLE
            }
        }
    }

}
