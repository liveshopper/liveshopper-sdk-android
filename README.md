![LiveShopper](https://raw.githubusercontent.com/liveshopper/liveshopper-sdk-android/master/docs/images/logo-small.svg?v=3&sanitize=true)

[LiveShopper](https://liveshopper.com) is the real time insight platform for mobile apps.

## Introduction

Integrate the SDK into your iOS and Android apps to start offering your users real-time, location targeted tasks and offers. The SDK abstracts away cross-platform differences between location services on iOS and Android, allowing you to introduce geofence based context to your apps with just a few lines of code.

The LiveShopper SDK is designed to be unobtrusive to the user and intelligently manage the frequency of tracking to efficiently consume battery.

## Authentication

Authenticate using your publishable API key, provided by LiveShopper.

## Configuration

The LiveShopper SDK uses the Play Services Location library. If you haven't already configured your project for Play Services, follow the instructions [here](https://developers.google.com/android/guides/google-services-plugin).

The following permissions are required, and are added automatically by the SDK manifest:

- `ACCESS_FINE_LOCATION`, for location services
- `INTERNET and ACCESS_NETWORK_STATE`, to send API requests
- `RECEIVE_BOOT_COMPLETED`, to restore geofences on boot

If targeting API level 29 or higher, to track the user's location in the background, you must also add the new `ACCESS_BACKGROUND_LOCATION` permission to your manifest. Learn more about [location permissions changes in Android 10](https://developer.android.com/preview/privacy/device-location).

## Add SDK to project

The best way to add the SDK to your project is via Gradle and [JCenter](https://bintray.com/bintray/jcenter) in Android Studio. Add the SDK to the dependencies section of your `build.gradle` file:

```kotlin
dependencies {
  implementation 'com.liveshopper:sdk:0.0.+'
}
```

The SDK depends on Google's v4 support library version `28.0.0` and higher (to check and request location permissions) and Play Services Location library version `16.0.0` and higher (for location services). These libraries will be automatically included as transitive dependencies. Learn more about managing dependencies in Gradle [here](https://docs.gradle.org/current/userguide/introduction_dependency_management.html#sec:dependency_resolution).

You can also add the SDK to your project manually, though this is not recommended. Download [the current release](https://github.com/liveshopper/liveshopper-sdk-android/releases) and unzip the package. The package contains an aar file. In Android Studio, add the SDK as a module using File > New Module > Import .JAR/.AAR Package.

The SDK currently supports API level `16` and higher.

The SDK is small and typically adds less than 500 KB to your compiled app.

## Integrate SDK into app

### Initialize SDK

Import the SDK:

```kotlin
import com.liveshopepr.sdk.LiveShopper
```

Initialize the SDK in your AppDelegate class, on the main thread, before calling any other LiveShopper methods. In application(\_:didFinishLaunchingWithOptions:), call:

```kotlin
LiveShopper.initialize(publishableKey)
```

### Identify user

Until you identify the user, the SDK will automatically identify the user via a unique device identifier.

To identify the user when logged in, call:

```kotlin
LiveShopper.setUserId(userId)
```

where userId is a unique ID for the user. We do not recommend using names, email addresses, etc. for this value.

### Request permissions

The SDK respects the built in  location permissions. Before tracking is permitted, the user must authorize location permissions for the app if they haven't previously. This is handled by the SDK once tracking is started.

To track the user's location in the foreground, the SDK requires the ACCESS_FINE_LOCATION permission. Learn more about requesting permissions here.

If targeting API level 29 or higher, to track the user's location in the background, the SDK also requires the new ACCESS_BACKGROUND_LOCATION permission.

```kotlin
// foreground only or targeting API level 28 and lower
ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)

// background and targeting API level 29 and higher
ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION), requestCode)
```

### Tracking

Once you have initialized the SDK and the user has granted permissions, you can start tracking the user's location in the background.

To start tracking the user's location, call:

```kotlin
LiveShopper.startTracking()
```

To stop tracking the user's location, call:

```kotlin
LiveShopper.stopTracking()
```

To listen for events or errors client-side in the background, create a class that extends `LiveShopperReceiver`. Then, register the receiver by adding a receiver element to the application element in your manifest:

```xml
        <receiver
                android:name=".ExampleReceiver"
                android:enabled="true"
                android:exported="false">
            <intent-filter>
                <action android:name="com.liveshopper.sdk.RECEIVED" />
            </intent-filter>
        </receiver>
```

Your receiver shoudl implement the following:

```kotlin
class ExampleReceiver : LiveShopperReceiver() {
    override fun onError(context: Context, status: LSStatus) {
        // do something with context, status
    }

    override fun onEventsReceived(context: Context, events: Array<LSEvent>, user: LSUser) {
        // do something with context, events, user
    }

    override fun onLocationUpdated(context: Context, location: Location, user: LSUser) {
        // do something with context, location, user
    }
}
```

### Tasks

The LiveShopper SDK uses the `LSTask` model to represent an activity that can be performed by the user.

`LSTask` provides the following information:

```json
{
  "count": 22,
  "data": [
    {
      "state": "generated",
      "description": "Determine if location exists",
      "distance": 0.41139203933289425,
      "due": "4102376400",
      "title": "Location Verification",
      "claimDistanceOverride": "0.5",
      "locationKey": "-LmfCNwykkNhhXEOYSB_",
      "logo": "",
      "taskRequirements": null,
      "associatesOnly": false,
      "nextQuestion": "-LmjvVzwDrCMSoYmz_7x",
      "location": {
        "latitude": 41.0388157,
        "longitude": -83.6532543,
        "address1": "229 W MAIN CROSS ST",
        "address2": "",
        "city": "FINDLAY",
        "country": "US",
        "name": "FINDLAY MAIN OFFICE",
        "phoneNumber": "",
        "state": "OH",
        "zipCode": "45840"
      },
      "owners": {
        "user": "...",
        "client": "...",
        "campaign": "-LmjvUny5xtQdOU3COwF"
      },
      "questions": [
        {
          "type": "singleAnswer",
          "order": 0,
          "images": null,
          "owners": {
            "client": "..."
          },
          "answers": [
            {
              "key": "-LmjvOS4y20duzH218WI",
              "score": null,
              "created": null,
              "modified": null,
              "answerOrder": 0,
              "displayText": "Yes",
              "questionKey": "-LmjvVzwDrCMSoYmz_7x",
              "displayTextKey": null,
              "clientReference": null,
              "photoCaptureOptions": null
            },
            {
              "key": "-LmjvQr5tYxA_dJftZRY",
              "score": null,
              "created": null,
              "modified": null,
              "answerOrder": 1,
              "displayText": "No",
              "questionKey": "-LmjvVzwDrCMSoYmz_7x",
              "displayTextKey": null,
              "clientReference": null,
              "photoCaptureOptions": null
            }
          ],
          "created": 1566319710,
          "videoId": null,
          "hasOther": false,
          "maxScore": 0,
          "maxValue": null,
          "minValue": null,
          "modified": 1569872078,
          "numStars": null,
          "pointers": {
            "previousKey": null,
            "nextKey": ""
          },
          "question": "Is this location an actual post office?",
          "parentKey": "-LmjvVzwDrCMSoYmz_7x",
          "isOptional": false,
          "answerOrder": "sorted",
          "allowDecimals": true,
          "lowValueLabel": null,
          "responseCount": null,
          "sentimentText": null,
          "highValueLabel": null,
          "scoreIntervals": null,
          "clientReference": "",
          "criticalAnswers": null,
          "showUnitsOnLeft": false,
          "thresholdAnswers": null,
          "maxMultipleAnswers": null,
          "minMultipleAnswers": null,
          "mustPickSuggestion": null,
          "singleLineResponse": null,
          "openTextPlaceholder": null,
          "openTextSuggestions": null,
          "photoCaptureOptions": {
            "photoLevelType": "none",
            "allowFlashToggle": true,
            "mustProvidePhoto": false,
            "photoOverlayType": "none",
            "defaultFlashState": false
          },
          "unitOfMeasurementLabel": null,
          "lastAnswerPositionPinned": null
        }
      ],
      "rewards": [
        {
          "logo": "",
          "state": "",
          "owners": {
            "client": "..."
          },
          "created": 1565199354,
          "message": {
            "body": "Stop by LiveShopper for your FREE high five!",
            "header": "High Five"
          },
          "legalese": "",
          "modified": 1565199354,
          "parentKey": "-Llh8g_IkORyRb3d7G0I",
          "claimCount": 0,
          "maxClaimCount": 0,
          "usedClaimCount": 0,
          "activationDelay": 0,
          "clientReference": "",
          "campaignRewardKey": "-Llh8g_IkORyRb3d7G0I",
          "activationExpiration": 1,
          "redemptionExpiration": 30
        }
      ],
      "time": {
        "max": 5,
        "min": 3
      }
    }
  ]
}
```

You can search for nearby tasks by calling:

```kotlin
LiveShopper.Tasks.get(
    latitude: Double,
    longitude: Double,
    radius: Double,
    minimumRadius: Double
) { result in
    if case let .success(tasks) = result {
        ...
    } else if case let .failure(error) = result {
        ...
    }
}
```

You can get the available tasks at a location by calling:

```kotlin
LiveShopper.Tasks.get(
    locationID: String,
    minimumRadius: Double?,
    campaignID: String?
) { result in
    if case let .success(tasks) = result {
        ...
    } else if case let .failure(error) = result {
        ...
    }
}
```

You can get details about what the user must do to satisfy the task by calling:

```kotlin
let requirements = LiveShopper.Tasks.getRequirements(task: LSTask)
```

You can claim a task for the current user by calling:

```kotlin
LiveShopper.Tasks.claim(task: LSTask) { result in
    if  case  let .success(claimedTask) = result {
        ...
    } else if  case  let .failure(error) = result
        ...
    }
}
```

You can send a user response related to a task by calling:

```kotlin
LiveShopper.Tasks.saveResponse(
    task: LSTask,
    question: LSQuestion,
    answers: [String]?,
    userAnswer: String?,
    image: UIImage?,
) { result in
    if  case  let .success(response) = result {
        ...
    else  if  case  let .failure(error) = result {
        ...
    }
}
```

Finally, there is a helper method to get the next step in a task by calling:

```kotlin
let question = LiveShopper.Tasks.nextQuestion(task: LSTask, key: String)
```

### Places

The LiveShopper SDK uses the `LSPlace` model to represent physical locations you have created.

`LSPlace` provides the following information:

```json
{
  "name": "FINDLAY MAIN OFFICE",
  "active": true,
  "latitude": 41.0388157,
  "longitude": -83.6532543,
  "address1": "229 W MAIN CROSS ST",
  "address2": "",
  "city": "FINDLAY",
  "state": "OH",
  "country": "US",
  "zipCode": "45840",
  "clientReference": "",
  "emails": [],
  "logoKey": "",
  "phoneNumber": ""
}
```

You can search for nearby `LSPlace` by calling:

```kotlin
LiveShopper.Places.get(
    latitude: Double,
    longitude: Double,
    radius: Double,
    minimumRadius: Double
) { result in
    if case .success = result {
        ...
    } else if case let .failure(error) = result {
        ...
    }
}
```

alternatively, you can query based on keyword:

```kotlin
LiveShopper.Places.get(searchTerm: String) { result in {
    if case .success = result {
        ...
    } else if case let .failure(error) = result {
        ...
    }
}
```

### Rewards

The LiveShopper SDK uses the `LSReward` model to represent what the user receives for completing a task.

`LSReward` provides the following information:

```json
{
  "logo": "",
  "state": "",
  "owners": {
    "client": "..."
  },
  "created": 1565199354,
  "message": {
    "body": "Stop by LiveShopper for your FREE high five!",
    "header": "High Five"
  },
  "legalese": "",
  "modified": 1565199354,
  "parentKey": "-Llh8g_IkORyRb3d7G0I",
  "claimCount": 0,
  "maxClaimCount": 0,
  "usedClaimCount": 0,
  "activationDelay": 0,
  "clientReference": "",
  "campaignRewardKey": "-Llh8g_IkORyRb3d7G0I",
  "activationExpiration": 1,
  "redemptionExpiration": 30
}
```

You can claim a `LSReward` by calling:

```kotlin
LiveShopper.Rewards.claim(
    task: LSTask,
    reward: LSReward
) { result in
    if case .success = result {
        ...
    } else if case let .failure(error) = result {
        ...
    }
}
```