package proj.tcg.turnonauta
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.aplicacio.Pagina_Principal
import proj.tcg.turnonauta.recuperar_contrasenya.RecuperarContrasenya
import proj.tcg.turnonauta.registre.Registre
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException

class PantallaLogin : AppCompatActivity() {
    private lateinit var bInici : Button
    private lateinit var tConObl : TextView
    private lateinit var tRegistre : TextView
    private lateinit var eTUsuari : EditText
    private lateinit var eTContra : EditText
    private var response : Int = -1

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
        bInici.setOnClickListener {
            checkLogin()
        }
        tConObl.setOnClickListener{
            val intent = Intent(this, RecuperarContrasenya::class.java)
            startActivity(intent)
        }
        tRegistre.setOnClickListener{
            val intent = Intent(this, Registre::class.java)
            startActivity(intent)
        }
    }

    /*
    "username": "user1",
    "password": "password1"
     */
    private fun checkLogin() {
        val username = eTUsuari.text.toString().trim()
        val password = eTContra.text.toString().trim()
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.API().getLogin(username,password)
                Log.d("User_ID Login:", "ID: "+response.toString())

                if (response > -1) {
                    Toast.makeText(this@PantallaLogin, "Login Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PantallaLogin, Pagina_Principal::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@PantallaLogin, "Invalid login credentials", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@PantallaLogin, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@PantallaLogin, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@PantallaLogin, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}