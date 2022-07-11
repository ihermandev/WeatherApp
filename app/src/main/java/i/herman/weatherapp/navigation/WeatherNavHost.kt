package i.herman.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import i.herman.weatherapp.locationdetail.view.LocationDetailsRoute
import i.herman.weatherapp.locationlist.contract.LocationListEvent
import i.herman.weatherapp.locationlist.view.LocationListRoute


@Composable
fun WeatherNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "add_location",
) {

    NavHost(navController = navController,
        startDestination = startDestination,
        modifier = modifier) {
        composable(route = "add_location") {
            LocationListRoute() { locationListEvent ->
                when (locationListEvent) {
                    LocationListEvent.OnLocationDetailsClick -> {
                        navController.navigate("location_details")
                    }
                }
            }
        }
        composable(route = "location_details") {
            LocationDetailsRoute()
        }
    }

}
