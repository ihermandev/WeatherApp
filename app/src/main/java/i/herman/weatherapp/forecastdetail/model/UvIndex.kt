package i.herman.weatherapp.forecastdetail.model

//TODO replace with a real measurement
sealed class UvIndex {
    object Low : UvIndex()
    object Moderate : UvIndex()
    object High : UvIndex()
    object VeryHigh : UvIndex()
    object Extreme : UvIndex()

    companion object {
        fun fromValue(value: Int): UvIndex {
            return when (value) {
                in 300..499 -> Moderate
                in 500..749 -> High
                in 750..949 -> VeryHigh
                in 950..Int.MAX_VALUE -> Extreme
                else -> Low
            }
        }
    }
}
