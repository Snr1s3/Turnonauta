package proj.tcg.turnonauta.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.aplicacio.EscriureCodi


class BottomMenu : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_bottom_menu, container, false)

        view.findViewById<ImageButton>(R.id.iBHistorial)
            .setOnClickListener {
               // val intent = Intent(activity, ::class.java)
               // startActivity(intent)
            }
        view.findViewById<ImageButton>(R.id.iBCodi)
            .setOnClickListener {
                val intent = Intent(activity, EscriureCodi::class.java)
                startActivity(intent)
            }
        view.findViewById<ImageButton>(R.id.iBUsuari)
            .setOnClickListener {
              //  val intent = Intent(activity, Header::class.java)
              //  startActivity(intent)
            }
        return view
    }

}