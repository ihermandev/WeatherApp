package i.herman.weatherapp.feature.forecastdetail.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import i.herman.weatherapp.R
import i.herman.weatherapp.feature.forecastdetail.contract.ForecastDetailEvent
import i.herman.weatherapp.feature.forecastdetail.contract.ForecastDetailViewIntent
import i.herman.weatherapp.feature.forecastdetail.contract.ForecastDetailViewState
import i.herman.weatherapp.feature.forecastdetail.model.*
import i.herman.weatherapp.feature.forecastdetail.viewmodel.ForecastDetailViewModel
import i.herman.weatherapp.feature.locationdetail.model.WeatherType
import i.herman.weatherapp.ui.core.ContentLoading

@Composable
fun ForecastDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: ForecastDetailViewModel = hiltViewModel(),
    onEvent: (ForecastDetailEvent) -> Unit,
) {

    val uiState: ForecastDetailViewState by viewModel.viewStateFlow.collectAsState()

    ForecastDetailScreen(
        modifier = modifier,
        state = uiState.state
    ) { forecastDetailViewIntent ->
        when (forecastDetailViewIntent) {
            is ForecastDetailViewIntent.FetchDetailedWeatherData -> {

            }
            is ForecastDetailViewIntent.OnBackClick -> {
                onEvent.invoke(ForecastDetailEvent.OnBackClick)
            }
        }
    }

}

@Composable
private fun ForecastDetailScreen(
    modifier: Modifier = Modifier,
    state: ForecastDetailState,
    forecastDetailViewIntent: (ForecastDetailViewIntent) -> Unit = {},
) {

    when (state) {
        is ForecastDetailState.EmptyForecast -> {

        }

        is ForecastDetailState.LoadingForecast -> {
            ContentLoading()
        }

        is ForecastDetailState.ForecastDetailedData -> {
            val forecastItem = state.forecast
            val currentDataItem = forecastItem.asForecastDetailedCurrentItem()

            LazyColumn(modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
            ) {
                item {
                    BriefForecastComponent(
                        weatherType = forecastItem.weatherType,
                        windSpeed = forecastItem.windSpeed,
                        currentTime = currentDataItem.time,
                        temp = currentDataItem.temperature,
                        apparentTemp = currentDataItem.apparentTemperature,
                        humidity = currentDataItem.humidity,
                        pressure = currentDataItem.pressure
                    )
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    HourlyForecastTempComponent(
                        title = "Hourly Forecast",
                        time = forecastItem.asListOfTime(),
                        weatherType = forecastItem.weatherTypeHourly,
                        temperature = forecastItem.temperature
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    SunriseSunsetComponent(
                        sunset = justTime(forecastItem.sunset),
                        sunrise = justTime(forecastItem.sunrise)
                    )
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    HourlyForecastCommonComponent(
                        time = forecastItem.asListOfTime(),
                        value = forecastItem.apparentTemperature,
                        iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_real_feel),
                        iconDesc = "Real Feel Temperature",
                        unit = "°C",
                        title = "Hourly Real Feel",
                    )
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    HourlyForecastCommonComponent(
                        time = forecastItem.asListOfTime(),
                        value = forecastItem.relativeHumidity,
                        iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_humidity),
                        iconDesc = "Humidity",
                        unit = "%",
                        title = "Hourly Humidity",
                    )
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    HourlyForecastCommonComponent(
                        time = forecastItem.asListOfTime(),
                        value = forecastItem.surfacePressure,
                        iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_pressure),
                        iconDesc = "Pressure",
                        unit = "hpa",
                        title = "Hourly Pressure",
                    )
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
                item {
                    HourlyForecastRadiationComponent(
                        time = forecastItem.asListOfTime(),
                        uvType = forecastItem.directRadiation,
                        title = "UV index"
                    )
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BriefForecastComponent(
    modifier: Modifier = Modifier,
    weatherType: WeatherType,
    windSpeed: Double,
    currentTime: String,
    temp: Double,
    apparentTemp: Double,
    humidity: Double,
    pressure: Double,
) {

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentTime.replace("T", ", "),
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.4f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = weatherType.weatherIcon),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(128.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$temp°C",
                style = MaterialTheme.typography.displayMedium.copy(
                    color = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.9f)
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = weatherType.weatherDescription,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.9f)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                ForecastItemDisplay(
                    value = apparentTemp.toString(),
                    measure = "°C",
                    iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_real_feel),

                    )

                Spacer(modifier = Modifier.width(48.dp))

                ForecastItemDisplay(
                    value = humidity.toString(),
                    measure = "%",
                    iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_humidity),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                ForecastItemDisplay(
                    value = pressure.toString(),
                    measure = "hPa",
                    iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_pressure),
                )

                Spacer(modifier = Modifier.width(36.dp))

                ForecastItemDisplay(
                    value = windSpeed.toString(),
                    measure = "km/h",
                    iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_wind),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SunriseSunsetComponent(
    modifier: Modifier = Modifier,
    sunset: String,
    sunrise: String,
) {
    Card(modifier = modifier) {
        Text(text = "Sunrise & Sunset",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.9f)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ForecastItemDisplayVertically(
                value = sunrise,
                measure = "",
                iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_sunrise),
                iconSize = 56.dp
            )
            ForecastItemDisplayVertically(
                value = sunset,
                measure = "",
                iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_sunset),
                iconSize = 56.dp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyForecastTempComponent(
    modifier: Modifier = Modifier,
    title: String,
    time: List<String>,
    weatherType: List<WeatherType>,
    temperature: List<Double>,
) {

    Card(modifier = modifier.fillMaxWidth()) {
        Text(text = title,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.9f)
            )
        )

        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(time) { index: Int, item: String ->
                ForecastHourlyItemDisplay(
                    time = item,
                    value = temperature[index],
                    unit = "°C",
                    icon = painterResource(id = weatherType[index].weatherIcon),
                    iconDesc = weatherType[index].weatherDescription
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyForecastRadiationComponent(
    modifier: Modifier = Modifier,
    title: String,
    time: List<String>,
    uvType: List<UvIndex>,
) {

    Card(modifier = modifier.fillMaxWidth()) {
        Text(text = title,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.9f)
            )
        )

        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(time) { index: Int, item: String ->
                ForecastHourlyUvIndexDisplay(
                    time = item,
                    value = uvType[index].uvDescription,
                    icon = ImageVector.vectorResource(uvType[index].icon),
                    iconDesc = uvType[index].uvDescription,
                    iconTint = colorResource(id = uvType[index].iconTint)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HourlyForecastCommonComponent(
    modifier: Modifier = Modifier,
    title: String,
    time: List<String>,
    iconVector: ImageVector,
    iconDesc: String? = null,
    value: List<Double>,
    unit: String,
) {

    Card(modifier = modifier.fillMaxWidth()) {
        Text(text = title,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.9f)
            )
        )

        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(time) { index: Int, item: String ->
                ForecastHourlyItemDisplay(
                    time = item,
                    value = value[index],
                    unit = unit,
                    icon = iconVector,
                    iconDesc = iconDesc
                )
            }
        }
    }
}

@Composable
fun ForecastHourlyItemDisplay(
    modifier: Modifier = Modifier,
    time: String,
    value: Double,
    unit: String,
    icon: Painter,
    iconDesc: String? = null,
    iconSize: Dp = 36.dp,
    textColorTop: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    textColorBottom: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = time,
            color = textColorTop
        )
        Image(
            painter = icon,
            contentDescription = iconDesc,
            modifier = Modifier
                .size(iconSize)
                .padding(vertical = 4.dp)
        )
        Text(
            text = "$value $unit",
            color = textColorBottom,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ForecastHourlyItemDisplay(
    modifier: Modifier = Modifier,
    time: String,
    value: Double,
    unit: String,
    icon: ImageVector,
    iconDesc: String? = null,
    iconSize: Dp = 36.dp,
    iconTint: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
    textColorTop: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    textColorBottom: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = time,
            color = textColorTop
        )
        Icon(
            imageVector = icon,
            tint = iconTint,
            contentDescription = iconDesc,
            modifier = Modifier
                .size(iconSize)
                .padding(vertical = 4.dp)
        )
        Text(
            text = "$value $unit",
            color = textColorBottom,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ForecastHourlyUvIndexDisplay(
    modifier: Modifier = Modifier,
    time: String,
    value: String,
    icon: ImageVector,
    iconDesc: String? = null,
    iconSize: Dp = 36.dp,
    iconTint: Color = MaterialTheme.colorScheme.secondary,
    textColorTop: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
    textColorBottom: Color = MaterialTheme.colorScheme.onSurface,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = time,
            color = textColorTop
        )
        Icon(
            imageVector = icon,
            tint = iconTint.copy(alpha = 0.7f),
            contentDescription = iconDesc,
            modifier = Modifier
                .size(iconSize)
                .padding(vertical = 4.dp)
        )
        Text(
            text = value,
            color = textColorBottom,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ForecastItemDisplay(
    modifier: Modifier = Modifier,
    value: String,
    measure: String,
    iconVector: ImageVector,
    iconSize: Dp = 24.dp,
    iconTint: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
        .copy(color = MaterialTheme.colorScheme.onSurface
            .copy(alpha = 0.9f)),
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = "Forecast Item",
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$value $measure",
            style = textStyle
        )
    }
}

@Composable
fun ForecastItemDisplayVertically(
    modifier: Modifier = Modifier,
    value: String,
    measure: String,
    iconVector: ImageVector,
    iconSize: Dp = 24.dp,
    iconTint: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
    textStyle: TextStyle = MaterialTheme.typography.labelSmall
        .copy(color = MaterialTheme.colorScheme.onSurface
            .copy(alpha = 0.9f)),
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = "Forecast Item",
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$value $measure",
            style = textStyle
        )
    }
}

@Preview
@Composable
fun ForecastItemDisplayPreview() {
    ForecastItemDisplay(
        value = "1000.0",
        measure = "hPa",
        iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_real_feel),
        textStyle = MaterialTheme.typography.titleSmall
    )
}

@Preview
@Composable
fun ForecastItemDisplayVerticallyPreview() {
    ForecastItemDisplayVertically(
        value = "2022-07-31",
        measure = "",
        iconVector = ImageVector.vectorResource(id = R.drawable.ic_forecast_sunset),
        textStyle = MaterialTheme.typography.titleSmall
    )
}

@Preview
@Composable
fun ForecastHourlyTempDisplayPreview() {
    ForecastHourlyItemDisplay(
        time = "19:00",
        value = 21.3,
        icon = painterResource(id = WeatherType.Cloudy.weatherIcon),
        unit = "°C"
    )
}

@Preview
@Composable
fun HourlyForecastComponentPreview() {
    HourlyForecastTempComponent(
        title = "Hourly Forecast",
        time = listOf("19:00", "08:00", "00:00", "10:00", "21:00"),
        temperature = listOf(21.3, 12.3, 21.5, 22.3, 21.3),
        weatherType = listOf(
            WeatherType.Sunny,
            WeatherType.Cloudy,
            WeatherType.HeavyRain,
            WeatherType.HeavyHailThunderstorm,
            WeatherType.PartlyCloudy)
    )
}

@Preview
@Composable
fun SunriseSunsetComponentPreview() {
    SunriseSunsetComponent(
        sunset = "20:32",
        sunrise = "04:56"
    )
}

@Preview
@Composable
fun BriefForecastComponentPreview() {
    BriefForecastComponent(
        weatherType = WeatherType.Sunny,
        windSpeed = 12.0,
        currentTime = "2022-07-15T18:00",
        temp = 23.3,
        apparentTemp = 24.2,
        humidity = 65.0,
        pressure = 1017.0
    )
}
