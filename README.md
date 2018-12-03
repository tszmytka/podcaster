# Podcaster
[![pipeline status](https://gitlab.com/tszmytka/podcaster/badges/master/pipeline.svg)](https://gitlab.com/tszmytka/podcaster/commits/master)
[![coverage report](https://gitlab.com/tszmytka/podcaster/badges/master/coverage.svg)](https://gitlab.com/tszmytka/podcaster/commits/master)

Application meant to allow searching and automatically finding podcasts and providing them to the configured media player.

## Main tasks
1. Find the appropriate podcast.
2. Obtain the correct url.
3. Open it up in the player.


## Technologies/libraries used
* Java 8
* JavaFx
* Spring Boot
* Gson
* jsoup


## Running in Java 11
The following need to be specified in order to run the application with Java 11: `--module-path <path_to_javafx_runtime> --add-modules=javafx.controls`
* https://openjfx.io/openjfx-docs/#install-javafx
