package proj.tcg.turnonauta.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.models.UsuariTorneig

class AdapterUsuarTorneig(private val list: List<UsuariTorneig>) :
    RecyclerView.Adapter<AdapterUsuarTorneig.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.usuari_recycled_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.imageView.setImageResource(item.image)
        holder.textView.text = item.text
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageview)
        val textView: TextView = view.findViewById(R.id.textView)
    }
}
