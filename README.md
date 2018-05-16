# Popular Movies

![Logo](../master/art/logo.png)

The Popular Movies Android app, was made for the Udacity's Android Developer Nanodegree Program.
This app has adaptive UI for phone and tablet devices. It displays the Most Popular and Top Rated Movies.
User has the ability to save favourite movies locally and view them even when is offline.
Also can view movie details (rating, release date, duration, etc.), watch trailers, read reviews and share the movie.

**Download:**

You can download an APK build [on releases page](https://github.com/dnKaratzas/udacity-popular-movies/releases/).

## How to Work with the Source
The app fetches movie information using [The Movie Database (TMDb)](https://www.themoviedb.org/documentation/api) API.
You have to enter your own API key into `gradle.properties` file.

```gradle.properties
MOVIE_DB_API_KEY="Your Api Key"
```

If you don’t already have an account, you will need to create one in order to request an [API Key](https://www.themoviedb.org/documentation/api) .

Screenshots
-----------
<img src="https://raw.githubusercontent.com/mattar99/PopularMovies-app/master/art/phone.png" alt="alt text" width="400"> <img src="https://raw.githubusercontent.com/mattar99/PopularMovies-app/master/art/phone-land.png" alt="alt text" width="400">

<img src="https://raw.githubusercontent.com/mattar99/PopularMovies-app/master/art/phone-details.png" alt="alt text" width="400"> <img src="https://raw.githubusercontent.com/mattar99/PopularMovies-app/master/art/phone-details2.png" alt="alt text" width="400">

<img src="https://raw.githubusercontent.com/mattar99/PopularMovies-app/master/art/tablet-land.png" alt="alt text" width="400"> <img src="https://raw.githubusercontent.com/mattar99/PopularMovies-app/master/art/tablet-port.png" alt="alt text" width="400">

<img src="https://raw.githubusercontent.com/mattar99/PopularMovies-app/master/art/tablet-details.png" alt="alt text" width="400">


Libraries
---------
* [Retrofit](https://github.com/square/retrofit)
* [Picasso](https://github.com/square/picasso)





