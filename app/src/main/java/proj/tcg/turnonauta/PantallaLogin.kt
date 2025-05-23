package proj.tcg.turnonauta

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.aplicacio.PaginaPrincipal
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.recuperar_contrasenya.RecuperarContrasenya
import proj.tcg.turnonauta.registre.Registre
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class PantallaLogin : AppCompatActivity() {
    private lateinit var bInici: Button
    private lateinit var tConObl: TextView
    private lateinit var tRegistre: TextView
    private lateinit var eTUsuari: EditText
    private lateinit var eTContra: EditText
    private lateinit var prefs: SharedPreferences
    private lateinit var appInstance: AppTurnonauta
    private var response: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.pantalla_login)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()

        prefs = getSharedPreferences("turnonauta_app", MODE_PRIVATE)
        appInstance = AppTurnonauta.getInstance()

        if (prefs.contains("userId")) {
            Log.d("User_ID Login:", "ID shared preferences: ${prefs.getInt("userID", 0)}")
            appInstance.setUserIdApp(prefs.getInt("userId", 0))
            val intent = Intent(this@PantallaLogin, PaginaPrincipal::class.java)
            startActivity(intent)
        }

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
            tConObl.text = getString(R.string.error_usuario_contrasenya_vacios)
            tConObl.visibility = View.VISIBLE
            return
        }

        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getLogin(username, password)
                Log.d("User_ID Login:", "ID: $response")
                if (response > -1) {
                    appInstance.setUserIdApp(response)
                    prefs.edit().putInt("userId", response).apply()
                    val intent = Intent(this@PantallaLogin, PaginaPrincipal::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    tConObl.text = getString(R.string.error_login_incorrecto)
                    tConObl.visibility = View.VISIBLE
                }
            } catch (e: HttpException) {
                tConObl.text = getString(R.string.error_servidor)
                tConObl.visibility = View.VISIBLE
            } catch (e: IOException) {
                tConObl.text = getString(R.string.error_conexion)
                tConObl.visibility = View.VISIBLE
            } catch (e: Exception) {
                tConObl.text = getString(R.string.error_desconegut)
                tConObl.visibility = View.VISIBLE
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            val prefs = it.getSharedPreferences("ajustes", MODE_PRIVATE)
            val lang = prefs.getString("app_language", "en") ?: "en"
            val context = setLocale(it, lang)
            super.attachBaseContext(context)
        } ?: super.attachBaseContext(newBase)
    }

    fun setLocale(context: Context, language: String): Context {
        val localeParts = language.split("-r")
        val locale =
            if (localeParts.size == 2) Locale(localeParts[0], localeParts[1]) else Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}
