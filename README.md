# MVVM Hilt

## Overview
This project is an Android application developed for an interview task. It fetches media coverages from a remote service and displays them in a paginated list. The app utilizes modern Android development practices and libraries such as Retrofit for network operations, Dagger Hilt for dependency injection, and Paging 3 for pagination.

## Development Environment
- **IDE**: Android Studio Iguana | 2023.2.1 Patch 2
- **Gradle Version**: 8.4
- **Android Gradle Plugin Version**: 8.3.2
- **Compile SDK**: 34
- **Min SDK**: 24
- **Target SDK**: 34

## Installation
To build and run the project locally, follow these steps:
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build the project using the provided Gradle configuration.

## Dependencies
The project uses several third-party libraries for various functionalities. Here are the main dependencies:
- **Retrofit**: For making network requests.
- **Dagger Hilt**: For dependency injection.
- **Paging 3**: For pagination of media coverages.
- **Coroutines**: For asynchronous programming.
- **AndroidX Libraries**: Including LiveData, ViewModel, RecyclerView, etc.
- **Other**: sdp & ssp Libraries used for maintaining resolution across different screen sizes.

For a detailed list of dependencies and versions, refer to the `build.gradle` file in the project.

## Features

- **Media Coverage Display**: Fetches media coverages from a remote service and displays them in a paginated list.
- **Image Loading and Caching**: Implements image loading and caching mechanism using a combination of LruCache and memory cache. It provides informative error messages or placeholders for failed image loads.
- **Error Handling**: Provides error handling for failed image loads, ensuring a smooth user experience.
- **Dependency Injection**: Utilizes Dagger Hilt for dependency injection to manage dependencies efficiently.
- **No Third-Party Libraries**: Implements image loading and caching mechanism without relying on any third-party libraries.

## Image Loading and Caching
The application follows the following approach for image loading and caching:
1. **Check Cache Memory**: It first checks if the image is available in the cache memory. If found, it directly loads the image from the cache.
2. **Local Path**: If the image is not available in the cache memory, it checks if a local path for the image is available. If found, it loads the image from the local path.
3. **URL**: If the image is not available in the cache memory or through a local path, it fetches the image from the provided URL.

## Usage
The app primarily consists of the following components:
- **MediaCoverageRepository**: Responsible for fetching media coverages from the remote service.
- **MediaCoverageViewModel**: Manages the UI-related data and interactions, including permission status and media coverages data.
- **MediaCoverageAdapter**: Adapts the media coverages data for display in a RecyclerView.
- **SquareGridRecyclerView**: A custom RecyclerView implementation for displaying items in a grid layout with square aspect ratio.

<p align="center" width="292" height="648">
  <img src="https://github.com/mr-lonewolfer/InterviewTask/raw/master/doc/AppUI.gif" alt="App UI GIF" width="292" height="648">
</p>


[Watch the App UI video](https://github.com/mr-lonewolfer/InterviewTask/blob/master/doc/AppUI.mp4)

[Download APK](https://github.com/mr-lonewolfer/InterviewTask/raw/master/doc/interviewTaskByNimesh_Debug.apk)

