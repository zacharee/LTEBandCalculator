package tk.zwander.ltebandcalculator.views

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.band_select_dialog.view.*
import tk.zwander.ltebandcalculator.R
import tk.zwander.ltebandcalculator.data.BandInfo
import tk.zwander.ltebandcalculator.util.BandSelectAdapter

class BandSelectionDialog(context: Context, bands: Map<Int, BandInfo>, updateCallback: () -> Unit) : MaterialAlertDialogBuilder(context) {
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.band_select_dialog, null)
        val adapter = BandSelectAdapter(bands, updateCallback)
        view.band_list.adapter = adapter
        setView(view)

        view.select_all.setOnClickListener {
            bands.forEach { (key, value) ->
                if (!value.isSelected) {
                    value.isSelected = true
                    adapter.notifyItemChanged(key)
                }
            }
            updateCallback()
        }

        view.deselect_all.setOnClickListener {
            bands.forEach { (key, value) ->
                if (value.isSelected) {
                    value.isSelected = false
                    adapter.notifyItemChanged(key)
                }
            }
            updateCallback()
        }

        setPositiveButton(android.R.string.ok, null)
    }
}