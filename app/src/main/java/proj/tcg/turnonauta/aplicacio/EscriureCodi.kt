package proj.tcg.turnonauta.aplicacio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.models.Torneig
import proj.tcg.turnonauta.retrofit.ConnexioAPI

import proj.tcg.turnonauta.screen.MenuInferiorAndroid
import retrofit2.HttpException
import java.io.IOException

class EscriureCodi : AppCompatActivity() {
    private lateinit var codiT : EditText
    private lateinit var bTorneig : Button
    private var response : Int = -1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escriure_codi)
        val menuInferior = MenuInferiorAndroid(window)
        menuInferior.hideSystemNavigationBar()
        codiT = findViewById(R.id.codiT)
        bTorneig = findViewById(R.id.bTorneig)

        bTorneig.setOnClickListener {
            val codi = codiT.text.toString().trim()
            if (codi.isEmpty()) {
                Toast.makeText(this, "El camp no pot ser buit", Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch {
                    try {
                        response = ConnexioAPI.api().getTornejosActiusId(codi.toInt())
                        Log.d("User_ID Login:", "ID: $response")

                        if (response > -1) {
                            val intent = Intent(this@EscriureCodi, PreviewTorneig::class.java)
                            val bundle = Bundle().apply {
                                putInt("idTorneig", 123)
                            }
                            intent.putExtras(bundle)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@EscriureCodi, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: HttpException) {
                        Toast.makeText(this@EscriureCodi, "Error del servidor, intente más tarde", Toast.LENGTH_SHORT).show()
                    } catch (e: IOException) {
                        Toast.makeText(this@EscriureCodi, "Problema de conexión, verifique su internet", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@EscriureCodi, "Ocurrió un error inesperado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


}