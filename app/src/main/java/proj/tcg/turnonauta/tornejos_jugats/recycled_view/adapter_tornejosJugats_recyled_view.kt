package proj.tcg.turnonauta.tornejos_jugats.recycled_view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.aplicacio.DetallTorneig
import proj.tcg.turnonauta.aplicacio.LlistaTornejosJugats
import proj.tcg.turnonauta.app.AppTurnonauta

class Adapter_tornejosJugats_recyled_view(private val context: Context, private val list: List<tornejosJugats_recyled_view>) :
    RecyclerView.Adapter<Adapter_tornejosJugats_recyled_view.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tornejos_jugats_recycled_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.textViewNom.text = item.nom
        holder.textViewCodi.text = holder.itemView.context.getString(R.string.codi_text, item.codi)
        holder.textViewJoc.text = holder.itemView.context.getString(R.string.joc_text, item.joc)
        holder.textViewFormat.text = holder.itemView.context.getString(R.string.format_text, item.format)
        holder.textViewJugadors.text = holder.itemView.context.getString(R.string.jugadors_text, item.jugadors)


        holder.itemView.setOnClickListener {
            val appInstance = AppTurnonauta.getInstance()
            appInstance.setTorneigIdApp(item.codi)
            val intent = when (context) {
                is LlistaTornejosJugats -> Intent(context, DetallTorneig::class.java)
                else -> null
            }
            intent?.let {
                it.putExtra("ITEM_ID", item.nom)
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNom: TextView = itemView.findViewById(R.id.nom)
        val textViewCodi: TextView = itemView.findViewById(R.id.codi)
        val textViewJoc: TextView = itemView.findViewById(R.id.joc)
        val textViewFormat: TextView = itemView.findViewById(R.id.format)
        val textViewJugadors: TextView = itemView.findViewById(R.id.jugadors)
    }
}
