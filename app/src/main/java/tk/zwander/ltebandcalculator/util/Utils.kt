package tk.zwander.ltebandcalculator.util

fun calculateBandNumber(selectedBands: Collection<Int>): Long {
    var bands = 0L

    selectedBands.forEach {
        if (it < 1) throw IllegalArgumentException("Band number must be > 1")
        bands = bands or (1L shl (it -1))
    }

    return bands
}