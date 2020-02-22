# NewsApp
Poject 8 and the final project at Udacity Android Basics nanodegree program

This is an app which retrieves data content (news) from the Guardian Web API.

The main requirement was to use AsyncTask Loader to retrieve data asynchronously.

While doing the project I practiced in parsing JSON data, working with Web Api and process incoming data, bind it to the view using RecyclerView adapter.
Another requirement was to handle corner cases gracefully like no connectivity or no data returned from the server.
I also added preference to the menu for filter news according to selected section, determine the number of news to be retrieved as well as order of the news.
Additionaly, I implemented asynchronouse retrieval of thumbnails.

## Pre-requisites
* Android SDK v29 (min SDK v21)
* Android Build Tools v29.0.3

## Getting Started
The app uses the Gradle build system. To build this project, use the "gradlew build" command or use "Import Project" in Android Studio.

## More todo
Initially I tried to implement infinite scrolling when user scrolls and data is retrieved from the internet gradually while all available data is retrived.
But I encountered differenet issues related to updating view in a beautiful way as well as maintain appropriate number of items during the scroll not to consume a lot of memory.
Bacuse of the deadline, I decided to switch to the simpler version where there is limited number of news which can be retrived and the number is controlled by preference.

## License
[MIT](https://opensource.org/licenses/MIT)

Copyright (c) 2020, Sergey Li
