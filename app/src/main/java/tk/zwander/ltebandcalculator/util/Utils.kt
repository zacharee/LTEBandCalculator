package tk.zwander.ltebandcalculator.util

/**
 * Do the magic of getting an NV value from a band.
 * Qualcomm's LTE NV format is pretty simple. In binary,
 * each LTE band is a digit, starting from the least-
 * significant (rightmost).
 *
 * For example, bands 1,2,3,5 would be 10111 in binary, or 23
 * in decimal.
 *
 * ==================================
 *
 * To calculate the value, take the bands the user has selected
 * (say 1,2,3,5) and loop through them. Each band is represented
 * by a 1, and its digits place is its number. Take the current
 * band sum (which starts at 0) and add to it. Shift that addition
 * left by (band_number - 1).
 *
 * For example, adding band 1 will add a
 * 1 shifted left 0 places. Adding band 2 will add a 1 shifted left 1
 * place. Adding band 3 will ass a 1 shifted left 2 places. And so on.
 *
 * The output in binary will be 10111, which is 23 in decimal.
 */
fun calculateBandNumber(selectedBands: Collection<Int>): Long {
    var bands = 0L

    selectedBands.forEach {
        if (it < 1) throw IllegalArgumentException("Band number must be > 1")
        bands = bands or (1L shl (it - 1))
    }

    return bands
}