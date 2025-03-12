package proj.tcg.turnonauta.config

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import proj.tcg.turnonauta.R

class EditarPerfil : AppCompatActivity() {
    private lateinit var botonImagen: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        botonImagen = findViewById(R.id.btnChangeName)

        botonImagen.setOnClickListener {
            val fragment = EditName_Fragment() // Instancia del fragment
            val fragmentManager: FragmentManager = supportFragmentManager
            fragment.show(fragmentManager, "editar_nombre_fragment") // Mostrar el fragment
        }
    }
}
