package i.herman.weatherapp.ui.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSimpleTopBar(
    title: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title ?: "") },
        colors = colors,
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar(
    title: String?,
    navigationIcon: ImageVector,
    navigationIconContentDescription: String?,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigationClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title ?: "") },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = colors,
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview("Simple Top App Bar")
@Composable
fun WeatherSimpleTopBarPreview() {
    WeatherSimpleTopBar(
        title = "Locations",
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
fun WeatherTopBarPreview() {
    WeatherTopBar(
        title = "Locations",
        navigationIcon = Icons.Default.ArrowBack,
        navigationIconContentDescription = "Back icon",
    )
}
