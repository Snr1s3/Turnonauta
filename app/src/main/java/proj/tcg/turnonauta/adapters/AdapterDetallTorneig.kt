package proj.tcg.turnonauta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.models.DetallTorneig

class AdapterDetallTorneig(private val list: List<DetallTorneig>) :
    RecyclerView.Adapter<AdapterDetallTorneig.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detall_torneig_recycled_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.textViewNom.text = item.nom
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewNom: TextView = itemView.findViewById(R.id.textView )
    }
}
