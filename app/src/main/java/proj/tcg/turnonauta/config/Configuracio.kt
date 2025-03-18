package proj.tcg.turnonauta.config


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import proj.tcg.turnonauta.PantallaLogin
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.aplicacio.Pagina_Principal

class Configuracio : AppCompatActivity() {
    private lateinit var botonImagen: ImageButton
    private lateinit var botonPerfil: MaterialButton
    private lateinit var botonLogOut: MaterialButton

    private var isRedBorder = false // Estado inicial (borde verde)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config) // Asegúrate de que el layout es correcto

        // Referencia al botón
        botonImagen = findViewById(R.id.btnNoti)
        botonPerfil = findViewById(R.id.btnPerfil)
        botonLogOut=findViewById(R.id.btnLogOut)

        botonImagen.setImageResource(R.drawable.campana_noti_on) // Imagen original
        botonImagen.setBackgroundResource(R.drawable.rounded_green_border_button) // Borde verde
        // Listener para cambiar imagen y borde al hacer clic
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

        botonPerfil.setOnClickListener {
            val intent = Intent(this, EditarPerfil::class.java)
            startActivity(intent)
        }
        botonLogOut.setOnClickListener{
            val intent= Intent(this,PantallaLogin::class.java)
            startActivity(intent)
        }
    }

}