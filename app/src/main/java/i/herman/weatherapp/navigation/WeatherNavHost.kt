package i.herman.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import i.herman.weatherapp.locationdetail.contract.LocationDetailEvent
import i.herman.weatherapp.locationdetail.view.LocationDetailsRoute
import i.herman.weatherapp.locationlist.contract.LocationListEvent
import i.herman.weatherapp.locationlist.view.LocationListRoute

@Composable
fun WeatherNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "add_location",
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = "add_location") {
            LocationListRoute() { locationListEvent ->
                when (locationListEvent) {
                    is LocationListEvent.OnLocationDetailsClick -> {
                        navController.navigate("location_details" +
                                "/${locationListEvent.location}" +
                                "/${locationListEvent.lat}" +
                                "/${locationListEvent.lng}")
                    }
                }
            }
        }
        composable(
            route = "location_details/{location}/{lat}/{lng}",
            arguments = listOf(
                navArgument("location") { type = NavType.StringType },
                navArgument("lat") { type = NavType.StringType },
                navArgument("lng") { type = NavType.StringType },
            )
        ) {
            LocationDetailsRoute() { locationDetailEvent ->
                when (locationDetailEvent) {
                    is LocationDetailEvent.OnWeatherDetailsClick -> {

                    }
                    is LocationDetailEvent.OnBackClick -> navController.popBackStack()
                }
            }
        }
    }
}

