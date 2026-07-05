<h1 align="center">
  <b>Appointments</b>
  <img src="https://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExd2x5ODRqeHdjbm1hajg3YjBzMmpwbnlzem51MWUyZHN3cTJidzgyaiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/AR1auiuk6pc5wiCPat/giphy.gif" width="35">
</h1>

## Description

Appointment is an Android application built with Kotlin that helps users organize appointments by combining scheduling, geolocation, and navigation features.

Each appointment contains a title, date, time, and destination. Users select destinations through an interactive map, where geographic coordinates are stored and later converted into human-readable addresses using Geocoder.

During navigation, the application retrieves the user's current location, requests the required location permissions, and queries Google Routes API to display multiple available routes, automatically highlighting the shortest one. Users can also update the appointment status directly from the navigation screen.

## Images

<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/72cdbc3f-e15e-485e-9a85-c8fc5df5807c" />
<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/18907233-2121-41b9-9292-b0db42cbb7ef" />
<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/ff08b758-36fd-4b3f-918a-af982e129ac4" />
<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/e074cc0e-926c-4156-800f-164bedd5c398" />
<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/ec103001-06ce-4a02-943c-c95642c53c1b" />
<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/d3f86106-83d6-4f92-bbc3-244227f72a12" />
<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/d97741c1-125a-40af-895d-ab67d05233b5" />
<img width="240" height="530" alt="image" src="https://github.com/user-attachments/assets/b37828b7-7365-47fc-9b34-d9a5fd98e127" />


## Features

- Create, edit, and delete appointments.
- Store appointment title, date, time, and destination.
- Interactive map for destination selection.
- Reverse geocoding using Geocoder.
- View all appointments in a list.
- Display appointments on Google Maps.
- Appointment status management:
  - Pending
  - On Route
  - Arrived
- Assisted navigation to the destination.
- Runtime location permission handling.
- GPS availability verification.
- Multiple route generation using Google Routes API.
- Automatic shortest route selection.
- Update appointment status during navigation.
- Local data persistence using Room Database.
- Local reminder notifications.

## Technologies

<span>
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white">
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white">
  <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white">

## Requirements

- Android Studio
- Android SDK 36
- Minimum SDK 29 (Android 10)
- Google Maps API Key
- Google Routes API Key

## Installation

```bash
https://github.com/LuisPaucarRodrigo/Appointment.git
```
Open the project in Android Studio.

Sync Gradle dependencies.

Run the application on a physical device or emulator with Google Play Services.

## Configuration

Before running the application, create or update the `local.properties` file with the following API keys:

```properties
MAPS_API_KEY=YOUR_GOOGLE_MAPS_API_KEY
ROUTES_API_KEY=YOUR_GOOGLE_ROUTES_API_KEY
```

### API Keys

| Key | Purpose |
|------|---------|
| `MAPS_API_KEY` | Used by Google Maps SDK to display interactive maps and markers. |
| `ROUTES_API_KEY` | Used to request navigation routes, calculate alternative paths, and determine the shortest route through Google Routes API. |

> **Note:** Both API keys must be enabled in Google Cloud Console with their corresponding APIs.

## Project Structure

The project follows Clean Architecture combined with MVVM, Repository Pattern, and Dependency Injection (Hilt) to keep the code modular, maintainable, and easy to test.

```kotlin
app
├── core/          # Shared infrastructure and common components
├── data/          # Data sources and repository implementations
├── domain/        # Business logic
└── ui/            # Presentation layer    
```
### Core

Contains reusable infrastructure shared across the application.

```kotlin
core
├── alarm               # AlarmManager scheduling
├── di                  # Hilt dependency injection modules
├── local.database      # Room database initialization
├── notification        # Notification manager, models & BroadcastReceiver
├── permissions         # Runtime permission helpers
├── remote.interceptor  # OkHttp interceptors
├── ui                  # Shared Compose UI components
└── utils               # Extensions and utility classes
```
### Data

Implements the application's data layer.

```kotlin
data
├── di                              # Data layer dependency injection
├── local
│   ├── database
│   │   ├── dao                     # Room DAO interfaces
│   │   └── entities                # Room entities
│   └── repositoriesImpl            # Local repository implementations
├── remoto
│   ├── entities                    # API request and response models
│   ├── repositoriesImpl            # Remote repository implementations
│   ├── services                    # Retrofit service interfaces
│   └── utils                       # Remote utilities (Polyline decoder)
└── system
    ├── location                    # Android Location services
    └── notification                # AppointmentNotificationScheduler
```
### Domain

Contains the application's business rules.

```kotlin
domain
├── appointment
│   ├── entities                    # Business models
│   ├── repositories                # Repository interfaces
│   ├── usecase                     # Application use cases
│   └── validate                    # Business validation rules
└── settings                        # GPS and location settings logic
```
### Ui

Presentation layer built using Jetpack Compose.

```kotlin
ui
├── components                      # Reusable Compose components
│   ├── hardware                    # Hardware-related components
│   └── layouts                     # Shared layouts
├── navigation                      # Navigation graphs and routes
├── screen
│   ├── detail                      # Appointment detail feature
│   ├── form                        # Create/Edit appointment feature
│   ├── history                     # Appointment history feature
│   └── list                        # Appointment list feature
└── theme                           # Material 3 theme configuration
```
