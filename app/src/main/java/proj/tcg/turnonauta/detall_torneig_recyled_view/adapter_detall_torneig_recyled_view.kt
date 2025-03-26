package proj.tcg.turnonauta.detall_torneig_recyled_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proj.tcg.turnonauta.R

class Adapter_detall_torneig_recyled_view(private val context: Context, private val list: List<detall_torneig_recyled_view>) :
    RecyclerView.Adapter<Adapter_detall_torneig_recyled_view.ViewHolder>() {

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
