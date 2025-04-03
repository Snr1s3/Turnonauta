package proj.tcg.turnonauta.config


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.aplicacio.PaginaPrincipal

class Configuracio : AppCompatActivity() {
    private lateinit var botonImagen: ImageButton
    private lateinit var botonPerfil: MaterialButton
    private lateinit var botonLogOut: MaterialButton
    private lateinit var botonModo: ImageButton
    private lateinit var botoPoliticas: MaterialButton
    private lateinit var botoTerms: MaterialButton
    private lateinit var botoUbi: ImageButton




    private var isRedBorder = false // Estado inicial (borde verde)
    private var isNight=false
    private var isUbiON=false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config) // Asegúrate de que el layout es correcto

        // Referencia al botón
        botonImagen = findViewById(R.id.btnNoti)
        botonPerfil = findViewById(R.id.btnPerfil)
        botonLogOut=findViewById(R.id.btnLogOut)
        botonModo=findViewById(R.id.btnModo)
        botoPoliticas=findViewById(R.id.btnPoliticas)
        botoTerms=findViewById(R.id.btnTerms)
        botoUbi=findViewById(R.id.btn_ubi)


        botonImagen.setImageResource(R.drawable.campana_noti_on)
        botonImagen.setBackgroundResource(R.drawable.rounded_green_border_button) // Borde verde

        botonModo.setImageResource(R.drawable.sol_turno)
        botonModo.setBackgroundResource(R.drawable.rounded_day_button)

        botoUbi.setBackgroundResource(R.drawable.rounded_green_border_button)
        botoUbi.setImageResource(R.drawable.location_on)

        botonModo.setOnClickListener {
            if(isNight) {
                botonModo.setImageResource(R.drawable.sol_turno)
                botonModo.setBackgroundResource(R.drawable.rounded_day_button)
            }else{
                botonModo.setImageResource(R.drawable.luna_turno)
                botonModo.setBackgroundResource(R.drawable.rounded_night_button)
            }
            isNight=!isNight

        }

        botonImagen.setOnClickListener {
            if (isRedBorder) {
                botonImagen.setImageResource(R.drawable.campana_noti_on) // Imagen original
                botonImagen.setBackgroundResource(R.drawable.rounded_green_border_button) // Borde verde
            } else {
                botonImagen.setImageResource(R.drawable.campana_noti_off) // Imagen cambiada
                botonImagen.setBackgroundResource(R.drawable.rounded_red_border_button) // Borde rojo
            }
            isRedBorder = !isRedBorder // Alternar estado
        }


        botoUbi.setOnClickListener {
            if (isUbiON) {
                botoUbi.setImageResource(R.drawable.location_on)
                botoUbi.setBackgroundResource(R.drawable.rounded_green_border_button)
            } else {
                botoUbi.setImageResource(R.drawable.location_off)
                botoUbi.setBackgroundResource(R.drawable.rounded_red_border_button)
            }
            isUbiON = !isUbiON
        }


        botonPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfil::class.java)
            startActivity(intent)
        }
        botonLogOut.setOnClickListener{
            val intent= Intent(this,PantallaLogin::class.java)
            startActivity(intent)
        }
        botoTerms.setOnClickListener{
            val intent= Intent(this,TermsOfUse::class.java)
            startActivity(intent)
        }
        botonLogOut.setOnClickListener{
            val intent= Intent(this,PoliticasPrivacidad::class.java)
            startActivity(intent)
        }
    }

}