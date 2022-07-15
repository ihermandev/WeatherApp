package i.herman.weatherapp.locationlist.view

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import i.herman.weatherapp.R
import i.herman.weatherapp.locationlist.contract.LocationListEvent
import i.herman.weatherapp.locationlist.contract.LocationListViewIntent
import i.herman.weatherapp.locationlist.contract.LocationListViewState
import i.herman.weatherapp.locationlist.model.LocationItem
import i.herman.weatherapp.locationlist.model.LocationListState
import i.herman.weatherapp.locationlist.util.TimeZoneBroadcastReceiver
import i.herman.weatherapp.locationlist.util.asExternalModel
import i.herman.weatherapp.locationlist.viewmodel.LocationListViewModel
import i.herman.weatherapp.ui.core.AnimatedSwipeDismiss
import i.herman.weatherapp.ui.core.ContentLoading
import i.herman.weatherapp.ui.core.WeatherSimpleTopBar
import kotlinx.datetime.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun LocationListRoute(
    modifier: Modifier = Modifier,
    viewModel: LocationListViewModel = hiltViewModel(),
    onEvent: (LocationListEvent) -> Unit,
) {
    val uiState: LocationListViewState by viewModel.viewStateFlow.collectAsState()

    val placesIntent =
        Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
            listOf(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS))
            .build(LocalContext.current)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            when (result.resultCode) {
                RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(it)
                        requireNotNull(place)
                        viewModel.processIntent(
                            LocationListViewIntent.AddLocation(location = place.asExternalModel()))
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.e("LocationScreen", "error=${status.statusMessage}")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    //TODO The user canceled the operation.
                }
            }
        }

    LocationListScreen(
        modifier = modifier,
        state = uiState.state,
        locationListIntent = { locationListIntent ->
            when (locationListIntent) {
                is LocationListViewIntent.FetchLocations -> {

                }
                is LocationListViewIntent.OnAddLocationClick -> {
                    launcher.launch(placesIntent)
                }
                is LocationListViewIntent.AddLocation -> {

                }
                is LocationListViewIntent.RemoveLocation -> {
                    viewModel.processIntent((LocationListViewIntent.RemoveLocation(
                        locationListIntent.location)))

                }
                is LocationListViewIntent.OnLocationClick -> {
                    onEvent.invoke(LocationListEvent.OnLocationDetailsClick(
                        location = locationListIntent.location,
                        lat = locationListIntent.lat,
                        lng = locationListIntent.lng
                    )
                    )
                }
            }
        },
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationListScreen(
    modifier: Modifier = Modifier,
    state: LocationListState,
    locationListIntent: (LocationListViewIntent) -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            WeatherSimpleTopBar(
                title = stringResource(id = R.string.locations_header),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { locationListIntent(LocationListViewIntent.OnAddLocationClick) }
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add Button")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        when (state) {
            is LocationListState.LocationChosen -> {}
            is LocationListState.LocationList -> {
                LocationList(
                    state = state,
                    innerPadding = innerPadding,
                    locationListIntent = locationListIntent
                )
            }
            is LocationListState.LoadingLocationList -> {
                ContentLoading()
            }
            is LocationListState.EmptyLocationList -> {
                LocationEmptyScreen()
            }
            is LocationListState.RemoveLocation -> {
                //TODO add android snackbar with the details
            }
        }
    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class
)
@Composable
private fun LocationList(
    state: LocationListState.LocationList,
    innerPadding: PaddingValues,
    locationListIntent: (LocationListViewIntent) -> Unit,
) {
    LazyColumn(
        contentPadding = innerPadding
    ) {
        items(items = state.locations, key = {
            it.hashCode()
        }) { location ->
            AnimatedSwipeDismiss(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 4.dp)
                    .animateItemPlacement(),
                item = location,
                background = { _ ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Red)
                            .padding(horizontal = 20.dp)
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier
                                .align(Alignment.CenterEnd),
                            tint = Color.White.copy(alpha = 0.9f)
                        )
                    }
                },
                content = {
                    LocationItemHolder(locationItem = location, modifier = Modifier.clickable {
                        locationListIntent(LocationListViewIntent.OnLocationClick(
                            location = location.name,
                            lat = location.lat,
                            lng = location.lng))
                    }
                    )
                },
                onDismiss = {
                    locationListIntent(LocationListViewIntent.RemoveLocation(location))
                }
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationItemHolder(
    modifier: Modifier = Modifier,
    locationItem: LocationItem,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            InfoLabelWithIcon(
                icon = Icons.Rounded.DateRange,
                title = dateFormatted(publishDate = locationItem.publishDate)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    Icons.Rounded.Place,
                    contentDescription = "Place",
                    tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = locationItem.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold)
                )
            }

            InfoLabelWithIcon(
                icon = Icons.Rounded.Info,
                title = "Lat/Lng: ${locationItem.lat} / ${locationItem.lng}",
            )
        }
    }
}

@Composable
private fun LocationEmptyScreen(
    modifier: Modifier = Modifier,
) {

    val infiniteTransition = rememberInfiniteTransition()

    val imgSize by infiniteTransition.animateFloat(
        initialValue = 100.0f,
        targetValue = 120.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                delayMillis = 100,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(imgSize.dp),
            painter = painterResource(id = R.drawable.ic_baseline_location_off),
            contentDescription = stringResource(id = R.string.no_locations_added_header),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary)
        )

        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier,
            text = stringResource(id = R.string.no_locations_added_header)
        )
    }
}

@Composable
private fun InfoLabelWithIcon(
    icon: ImageVector,
    title: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = "Publish Date",
            tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.8f)
            )
        )
    }
}

@Composable
private fun dateFormatted(publishDate: Instant): String {
    var zoneId by remember { mutableStateOf(ZoneId.systemDefault()) }

    val context = LocalContext.current

    DisposableEffect(context) {
        val receiver = TimeZoneBroadcastReceiver(
            onTimeZoneChanged = { zoneId = ZoneId.systemDefault() }
        )
        receiver.register(context)
        onDispose {
            receiver.unregister(context)
        }
    }

    return DateTimeFormatter.ofPattern("HH:mm, dd/MM/yy")
        .withZone(zoneId).format(publishDate.toJavaInstant())
}


@Preview
@Composable
fun LocationListScreenPreview() {
    LocationListScreen(
        state = LocationListState.LocationList(locations = listOf(
            LocationItem(
                name = "Warsaw", lat = 52.237049, lng = 21.017532,
                publishDate = LocalDateTime(
                    year = 2022,
                    monthNumber = 5,
                    dayOfMonth = 4,
                    hour = 23,
                    minute = 0,
                    second = 0,
                    nanosecond = 0
                ).toInstant(TimeZone.UTC),
            ),
            LocationItem(
                name = "Kyiv", lat = 50.450001, lng = 30.523333,
                publishDate = LocalDateTime(
                    year = 2022,
                    monthNumber = 5,
                    dayOfMonth = 4,
                    hour = 23,
                    minute = 0,
                    second = 0,
                    nanosecond = 0
                ).toInstant(TimeZone.UTC),
            )
        )))
}

@Preview
@Composable
fun LocationListScreenLoadingPreview() {
    LocationListScreen(state = LocationListState.LoadingLocationList)
}

@Preview
@Composable
fun LocationEmptyScreenPreview() {
    LocationEmptyScreen()
}

@Preview
@Composable
fun LocationItemHolderPreview() {
    LocationItemHolder(
        locationItem = LocationItem(
            name = "Kyiv", lat = 50.450001, lng = 30.523333,
            publishDate = LocalDateTime(
                year = 2022,
                monthNumber = 5,
                dayOfMonth = 4,
                hour = 23,
                minute = 0,
                second = 0,
                nanosecond = 0
            ).toInstant(TimeZone.UTC),
        )
    )
}
