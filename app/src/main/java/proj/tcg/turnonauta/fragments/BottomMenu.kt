package proj.tcg.turnonauta.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.aplicacio.EscriureCodi
import proj.tcg.turnonauta.aplicacio.LlistaTornejosJugats


class BottomMenu : Fragment() {

    companion object {
        fun newInstance(userId: Int): BottomMenu {
            val fragment = BottomMenu()
            val bundle = Bundle()
            bundle.putInt("user_id", userId)
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_bottom_menu, container, false)
        val userId = arguments?.getInt("user_id") ?: -1
        Log.d("BottomMenu", "Received User_ID in Fragment: $userId")
        view.findViewById<ImageButton>(R.id.iBHistorial)
            .setOnClickListener {
                val intent = Intent(activity, LlistaTornejosJugats::class.java).apply {
                    putExtra("user_id", userId)
                }
                startActivity(intent)
            }
        view.findViewById<ImageButton>(R.id.iBCodi)
            .setOnClickListener {
                val intent = Intent(activity, EscriureCodi::class.java).apply {
                    putExtra("user_id", userId)
                }
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