# Setup
In order to build the project GOOGLE_PLACES_API_KEY is required

## Technology Stack
* Kotlin Coroutines with Flow
* MVI
* Jetpack Compose
* Room
* Hilt
* Retrofit
* Google Places API
* kotlinx-datetime
## Functionality 
Application should contain following screens: 
- Weather forecast
- Forecast details  
- User locations
### User locations
- User should be able to add any location 
- All added locations shown as list of locations 
- By clicking on location item user navigated to weather forecast screen 
### Weather forecast 
- Show 7 days (from today) weather forecast for selected location 
- Weather forecast screen show basic weather information, which includes: 
  - Forecast date 
  - Temperature 
  - Weather icon 
- By clicking on weather item user navigated to weather details screen  
### Weather details screen 
- Show weather details for the selected day 
- Weather details screen should show following information: 
  - Temperature day, night, feels like 
  - Weather icon 
  - Humidity 
  - Pressure 
  - UV index 
  - Sunrise and sunset time
### Demo

https://user-images.githubusercontent.com/25265967/179420588-14719e46-5fb3-4d40-a610-4303603f31f5.mp4


