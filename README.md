# WeatherAndroidApplication

This application build in Kotlin using the following dependencies,

    Dagger2 - Used to implement dependencies
    Coroutine - for handlding threads
    Room-Persistence Library - for caching data to local database
    Retrofit2- for network handling

ViewModel and Live data is used with MVVM Architecture. User can search any city to get 5 day weather report. Using the weather data obtained, only few details is showcased to the User for maintaing a good UI rather than keeping a complex UI.

User need INTERNET at first to cache the data to local database. 

When User having the INTERNET connection,
Always priority goes for the remote server to fetch the data.  If the data received from the remoete server, the local database will be updated with the new data, by deleting the old data for the same query function. 

When User havn't any INTERNET Connection,
Query search will be done in Local Database. No other option to make it.

What if I have more time,

    1.Implement more UI with animation for loading the text, and list of data in recyclerview.
    2.Write full unit testing, and UI testing using Espresso.
    3.Handle more user issues.
    4.Think of a better UI than this.
    5.Showing suggestion for user while typing the city.
