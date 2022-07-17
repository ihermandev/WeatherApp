package i.herman.weatherapp.feature.forecastdetail.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import i.herman.weatherapp.R

//TODO replace with a real measurement
sealed class UvIndex(
    val uvDescription: String,
    @DrawableRes
    val icon: Int = R.drawable.ic_forecast_uv_index,
    @ColorRes
    val iconTint: Int,
) {
    object Low : UvIndex(
        uvDescription = "Low",
        iconTint = R.color.uv_index_low
    )

    object Moderate : UvIndex(
        uvDescription = "Moderate",
        iconTint = R.color.uv_index_moderate
    )

    object High : UvIndex(
        uvDescription = "High",
        iconTint = R.color.uv_index_high
    )

    object VeryHigh : UvIndex(
        uvDescription = "Very High",
        iconTint = R.color.uv_index_very_high
    )

    object Extreme : UvIndex(
        uvDescription = "Extremely High",
        iconTint = R.color.uv_index_extreme
    )

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
