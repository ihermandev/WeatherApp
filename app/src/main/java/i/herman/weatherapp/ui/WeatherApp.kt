package i.herman.weatherapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import i.herman.weatherapp.navigation.WeatherNavHost
import i.herman.weatherapp.ui.theme.WeatherAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp() {
    WeatherAppTheme {
        val navController = rememberNavController()

        Scaffold(modifier = Modifier, containerColor = MaterialTheme.colorScheme.onBackground) {
            WeatherNavHost(navController = navController)
        }
    }
}
