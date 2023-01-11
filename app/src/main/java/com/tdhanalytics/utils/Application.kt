package com.tdhanalytics.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.HashMap

var LOG_TAG = "Ana Log"
var analyticsApp = ArrayList<AnalyticsProvider>()

interface AnalyticsProvider {
    fun start()
    fun logEvent(eventName: String, eventValues: HashMap<String, Any>)
}

class AppsFlyerAnalyticsProviders (context: Context, afDevKey: String, isDebug: Boolean): AnalyticsProvider {
    var context: Context = context
    var afDevKey = afDevKey
    var appsflyer = AppsFlyerLib.getInstance()

    init {
        appsflyer.setDebugLog(isDebug)
        appsflyer.init(afDevKey, null, context)
    }

    override fun start() {
        appsflyer.start(context, afDevKey, object : AppsFlyerRequestListener {
            override fun onSuccess() {
                Log.d(LOG_TAG, "Launch sent successfully waaa")
            }

            override fun onError(errorCode: Int, errorDesc: String) {
                Log.d(LOG_TAG, "Launch failed to be sent:\n" +
                        "Error code: " + errorCode + "\n"
                        + "Error description: " + errorDesc)
            }
        })
    }

    override fun logEvent(eventName: String, eventValues: HashMap<String, Any>) {
        appsflyer.logEvent(
            context,
            eventName, eventValues, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Log.d(LOG_TAG, "$eventName $eventValues")
                    Log.d(LOG_TAG, "Event sent successfully wa")
                }

                override fun onError(errorCode: Int, errorDesc: String) {
                    Log.d(
                        LOG_TAG, "Event failed to be sent:\n" +
                                "Error code: " + errorCode + "\n"
                                + "Error description: " + errorDesc
                    )
                }
            })
    }

}

class FirebaseAnalyticsProvider(firebaseContext: FirebaseAnalytics): AnalyticsProvider {
    var firebaseContext = firebaseContext

    override fun start() {}

    override fun logEvent(eventName: String, eventValues: HashMap<String, Any>) {
        val bundle = Bundle()

        bundle.putString("show_name", "test value")

        firebaseContext.logEvent(eventName, bundle)

        Log.d(LOG_TAG, "$eventName $bundle")
    }
}

object TDHAnalytics {
    fun use(analytic: AnalyticsProvider) {
        analyticsApp.add(analytic)
    }

    fun start() {
        analyticsApp.forEach {
            it.start()
        }
    }

    fun logEvent(eventName: String, eventValues: HashMap<String, Any>) {
        analyticsApp.forEach {
            it.logEvent(eventName, eventValues)
        }
    }
}
