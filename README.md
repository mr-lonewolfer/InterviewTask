# Interview Task

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

- **Image Grid**: Displays media coverages in a 3-column square image grid with center cropping.
- **Asynchronous Image Loading**: Implements asynchronous image loading using provided URLs.
- **Pagination**: Allows users to scroll through at least 100 images with smooth pagination.
- **Caching Mechanism**: Implements caching of images retrieved from the API in both memory and disk cache for efficient retrieval.
- **Error Handling**: Gracefully handles network errors and image loading failures, providing informative error messages or placeholders for failed image loads.

## Implementation Details

- **Networking**: Utilizes Retrofit for network operations to fetch media coverages from the remote service.
- **Dependency Injection**: Implements Dagger Hilt for dependency injection to manage dependencies efficiently.
- **Pagination**: Implements Paging 3 library for pagination of media coverages, ensuring smooth scrolling and efficient memory usage.
- **Image Loading**: Implements asynchronous image loading using provided URLs, with caching mechanism to store images locally for efficient retrieval.
- **Error Handling**: Handles network errors and image loading failures gracefully, ensuring a seamless user experience even in adverse conditions.

## Usage
The app primarily consists of the following components:
- **MediaCoverageRepository**: Responsible for fetching media coverages from the remote service.
- **MediaCoverageViewModel**: Manages the UI-related data and interactions, including permission status and media coverages data.
- **MediaCoverageAdapter**: Adapts the media coverages data for display in a RecyclerView.
- **SquareGridRecyclerView**: A custom RecyclerView implementation for displaying items in a grid layout with square aspect ratio.
