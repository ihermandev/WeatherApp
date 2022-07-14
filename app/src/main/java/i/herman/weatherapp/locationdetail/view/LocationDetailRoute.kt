package i.herman.weatherapp.locationdetail.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import i.herman.weatherapp.R
import i.herman.weatherapp.locationdetail.contract.LocationDetailEvent
import i.herman.weatherapp.locationdetail.contract.LocationDetailViewIntent
import i.herman.weatherapp.locationdetail.contract.LocationDetailViewState
import i.herman.weatherapp.locationdetail.model.ForecastItem
import i.herman.weatherapp.locationdetail.model.LocationDetailState
import i.herman.weatherapp.locationdetail.model.WeatherType
import i.herman.weatherapp.locationdetail.viewmodel.LocationDetailViewModel
import i.herman.weatherapp.ui.core.ContentLoading
import i.herman.weatherapp.ui.core.WeatherTopBar

@Composable
fun LocationDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: LocationDetailViewModel = hiltViewModel(),
    onEvent: (LocationDetailEvent) -> Unit,
) {

    val uiState: LocationDetailViewState by viewModel.viewStateFlow.collectAsState()

    LocationDetailsScreen(
        modifier = modifier,
        topBarTitle = viewModel.locationName,
        state = uiState.state,
        locationDetailIntent = { locationDetailIntent ->
            when (locationDetailIntent) {
                is LocationDetailViewIntent.FetchSevenDaysWeatherForecast -> {}
                is LocationDetailViewIntent.OnBackClick -> {
                    onEvent.invoke(LocationDetailEvent.OnBackClick)
                }
                is LocationDetailViewIntent.OnWeatherClick -> {}
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(
    modifier: Modifier = Modifier,
    topBarTitle: String = "",
    state: LocationDetailState,
    locationDetailIntent: (LocationDetailViewIntent) -> Unit = {},
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            WeatherTopBar(
                title = topBarTitle,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = Icons.Rounded.ArrowBack,
                navigationIconContentDescription = stringResource(id = R.string.arrow_back),
                onNavigationClick = { locationDetailIntent(LocationDetailViewIntent.OnBackClick) }
            )
        }) { innerPadding ->
        when (state) {
            is LocationDetailState.EmptyForecast -> {
            }
            is LocationDetailState.ForecastChosen -> {

            }
            is LocationDetailState.ForecastList -> {
                LazyColumn(
                    modifier = modifier.padding(top = 16.dp),
                    contentPadding = innerPadding
                ) {
                    items(items = state.forecasts) {
                        ForecastItemHolder(
                            item = it,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                        )
                    }
                }
            }
            is LocationDetailState.LoadingForecast -> {
                ContentLoading()
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ForecastItemHolder(
    modifier: Modifier = Modifier,
    item: ForecastItem,
) {

    Card(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(8.dp)) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = item.weatherType.weatherIcon),
                contentDescription = item.weatherType.weatherDescription
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column(modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = item.time,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.4f)))
                }
                Text(
                    text = item.weatherType.weatherDescription,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                TemperatureLabelWithIcon(
                    icon = Icons.Rounded.KeyboardArrowUp,
                    iconTint = Color.Green,
                    title = "Max temp",
                    content = item.temperatureMax.toInt().toString()
                )

                Spacer(modifier = Modifier.size(4.dp))

                TemperatureLabelWithIcon(
                    icon = Icons.Rounded.KeyboardArrowDown,
                    iconTint = Color.Red,
                    title = "Min temp",
                    content = item.temperatureMin.toInt().toString()
                )
            }
        }
    }
}

@Composable
private fun TemperatureLabelWithIcon(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    content: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = iconTint.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = "$title $content °C",
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(name = "Foggy Forecast")
@Composable
fun ForecastItemHolderFoggyPreview() {
    ForecastItemHolder(
        item = ForecastItem(temperatureMax = 19.9,
            temperatureMin = 12.4,
            time = "2021-11-11",
            weatherType = WeatherType.Foggy
        )
    )
}

@Preview(name = "ThunderstormForecast")
@Composable
fun ForecastItemHolderThunderstormPreview() {
    ForecastItemHolder(
        item = ForecastItem(temperatureMax = 19.9,
            temperatureMin = 12.4,
            time = "2021-11-11",
            weatherType = WeatherType.HeavyHailThunderstorm
        )
    )
}
