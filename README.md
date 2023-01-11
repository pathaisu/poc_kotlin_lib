# TDHAnalytics Library

Android library to handle various type of analytics such as `AppFlyer` and `Firebase Analytics`.

**Util Methods**

|Method|Arguments|Description|
|:---:|:---:|---|
|use()|providers|add analytics provider|
|addSessionParameter()|session name with parameters|add session parameters|
|start()|-|initiate tracking session|
|logEvent()|event name and event value|log event|

**Providers**

|Provider|Description|
|:------:|:---------:|
|AppsFlyerAnalyticsProvider()|a helper function to init AppFlyer|
|FirebaseAnalyticsProvider()|a helper function to handle Firebase|


How to use
----

step 1: initiate analytics session.

```kt
// MainApplication.ts

import com.anaytist.utils.TDHAnalytics
import com.anaytist.utils.AppsFlyerAnalyticsProvider

class MainApplication : Application() {
  ...

  override fun onCreate() {
    var afAppID = "xDe..."
    TDHAnalytics.use(AppsFlyerAnalyticsProvider(instance, afAppID, true))

    TDHAnalytics.start()
  }
}

```

step 2: star event logging.

```kt
// AnalyticsMangerImpl.kt

import com.anaytist.utils.TDHAnalytics

class AnalyticsManagerImpl : AnalyticsManager {
    private val firebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    override fun init(context: Context) {
      ...
      TDHAnalytics.use(FirebaseAnalyticsProvider(firebaseAnalytics))
    }
}
```