package tk.zwander.ltebandcalculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import tk.zwander.ltebandcalculator.data.BandInfo
import tk.zwander.ltebandcalculator.util.calculateBandNumber
import tk.zwander.ltebandcalculator.views.BandSelectionDialog
import java.util.*

class MainActivity : AppCompatActivity() {
    private val cbm by lazy { getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }

    private val bands = TreeMap<Int, BandInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        copy_result.setOnClickListener {
            val clip= ClipData.newPlainText("NV Data", result_field.text)
            cbm.primaryClip = clip
        }

        select_bands.setOnClickListener {
            BandSelectionDialog(this, bands) { updateResult() }
                .show()
        }

        loadInBands()
    }

    private fun updateResult() {
        val selectedBands = bands.values.filter { it.isSelected }.map { it.band }
        val result = calculateBandNumber(selectedBands)

        result_field.setText(result.toString())
        selected_bands_field.setText(selectedBands.joinToString(","))
    }

    private fun loadInBands() {
        val bandsArray = resources.getStringArray(R.array.lte_bands)
        bandsArray.forEachIndexed { index, band ->
            val split = band.split("/")
            val bandInfo = BandInfo(split[0].toInt(), split[1].toInt(), split[2])

            bands[index] = bandInfo
        }
    }
}