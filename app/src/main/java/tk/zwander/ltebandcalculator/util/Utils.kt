package tk.zwander.ltebandcalculator.util

import com.ionspin.kotlin.bignum.integer.BigInteger

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
 * One possible way to calculate the value is to take the bands
 * the user has selected (say 1,2,3,5) and loop through them.
 * Each band is represented by a 1, and its digits place is its number.
 * Take the current band sum (which starts at 0) and add to it.
 * Shift that addition left by (band_number - 1).
 *
 * For example, adding band 1 will add a
 * 1 shifted left 0 places. Adding band 2 will add a 1 shifted left 1
 * place. Adding band 3 will add a 1 shifted left 2 places. And so on.
 *
 * The output in binary will be 10111, which is 23 in decimal.
 *
 * ==================================
 *
 * However, by the time we reach somewhere between bands 53 and 65,
 * we hit a Long overflow. Even using a BigInteger implementation, it
 * fails for some reason. So instead, we can just use 2^(band - 1).
 * This does the same thing as the bit shifting, but in base-10,
 * and seems to work more reliably.
 */
@ExperimentalUnsignedTypes
fun calculateBandNumber(selectedBands: Collection<Int>): BigInteger {
    var bands = BigInteger(0)

    selectedBands.forEach {
        if (it < 1) throw IllegalArgumentException("Band number must be > 1")
//        bands = bands or (BigInteger(1).shl(it - 1))
        bands += BigInteger(2).pow(BigInteger(it - 1))
    }

    return bands
}

@ExperimentalUnsignedTypes
fun calculateListFromBandNumber(bandNumber: String): List<Int> {
    return calculateListFromBandNumber(BigInteger.parseString(bandNumber))
}

@ExperimentalUnsignedTypes
fun calculateListFromBandNumber(bandNumber: BigInteger): List<Int> {
    val bands = ArrayList<Int>()

    bandNumber.toString(2).reversed().forEachIndexed { index, c ->
        if (c == '1') bands.add(index + 1)
    }

    return bands
}