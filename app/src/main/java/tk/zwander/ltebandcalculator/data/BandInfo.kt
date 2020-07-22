package tk.zwander.ltebandcalculator.data

data class BandInfo(
    val band: Int,
    val freq: Int,
    val desc: String,
    var isSelected: Boolean = false
) : Comparable<BandInfo> {
    override fun compareTo(other: BandInfo): Int {
        return band.compareTo(other.band)
    }

//    override fun equals(other: Any?): Boolean {
//        return other is BandInfo
//                && band == other.band
//                && freq == other.freq
//                && desc == other.desc
//    }
//
//    override fun hashCode(): Int {
//        var result = band
//        result = 31 * result + freq
//        result = 31 * result + desc.hashCode()
//        return result
//    }
}