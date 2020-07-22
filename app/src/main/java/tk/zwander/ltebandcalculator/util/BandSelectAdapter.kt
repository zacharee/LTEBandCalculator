package tk.zwander.ltebandcalculator.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lte_band_item.view.*
import tk.zwander.ltebandcalculator.R
import tk.zwander.ltebandcalculator.data.BandInfo

class BandSelectAdapter(private val bands: Map<Int, BandInfo>, private val updateCallback: () -> Unit) : RecyclerView.Adapter<BandSelectAdapter.VH>() {
    override fun getItemCount(): Int {
        return bands.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.lte_band_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(bands[position] ?: error("Invalid adapter position?"))
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val currentInfo: BandInfo
            get() = bands[adapterPosition] ?: error("Invalid adapter position?")

        fun onBind(info: BandInfo) {
            itemView.apply {
                band_card.isChecked = info.isSelected

                band_card.setOnClickListener {
                    band_card.apply { isChecked = !isChecked }
                }

                band_card.setOnCheckedChangeListener { _, isChecked ->
                    currentInfo.isSelected = isChecked
                    updateCallback()
                }

                band_name.text = info.band.toString()
                band_freq.text = resources.getString(R.string.band_freq_template, info.freq)
                band_desc.text = info.desc
            }
        }
    }
}