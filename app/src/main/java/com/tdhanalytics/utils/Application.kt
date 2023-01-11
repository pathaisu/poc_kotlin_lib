package com.tdhanalytics.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.HashMap


var appsflyer = AppsFlyerLib.getInstance()
var LOG_TAG = "Ana Log"
var appFlyerKey = ""
var tools = ArrayList<String>()
var instance: Context? = null
var sessionParameters = HashMap<String, Any>()

var firebaseInstance: FirebaseAnalytics? = null

fun AppsFlyerAnalyticsProvider(context: Context, afDevKey: String, isDebug: Boolean): String {
    // For debug - remove in production
    // For debug - remove in production
    appsflyer.setDebugLog(isDebug)

    appsflyer.init(afDevKey, null, context)

    appFlyerKey = afDevKey

    instance = context

    return "appFlyer"
}

fun FirebaseAnalyticsProvider(firebaseContext: FirebaseAnalytics): String {
    firebaseInstance = firebaseContext

    return "firebase"
}

object TDHAnalytics {
    fun use(tool: String) {
        Log.d(LOG_TAG, "Platform $tool")
        tools.add(tool)
    }

    fun addSessionParameter(params: HashMap<String, Any>) {
        sessionParameters = params
    }

    fun start() {
        if (tools.contains("appFlyer")) {
            instance?.let {
                appsflyer.start(it, appFlyerKey, object : AppsFlyerRequestListener {
                    override fun onSuccess() {
                        Log.d(LOG_TAG, "Launch sent successfully woi")
                    }

                    override fun onError(errorCode: Int, errorDesc: String) {
                        Log.d(LOG_TAG, "Launch failed to be sent:\n" +
                                "Error code: " + errorCode + "\n"
                                + "Error description: " + errorDesc)
                    }
                })
            };
        }
    }

    fun logEvent(eventName: String, eventValues: HashMap<String, Any>) {
        // val eventValues = HashMap<String, Any>()
        // eventValues.put(AFInAppEventParameterName.PRICE, 1234.56)
        // eventValues.put(AFInAppEventParameterName.CONTENT_ID,"1234567")

        if (tools.contains("appFlyer")) {
            instance?.let {
                appsflyer.logEvent(
                    it,
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

        if (tools.contains("firebase")) {
            val bundle = Bundle()

            bundle.putString("show_name", "test value")

            firebaseInstance?.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

            Log.d("Ana Logger", "Fireeeeeee baseeeeeee firerrrr")
        }

        Log.d("Ana Logger", "---- > > > new ver na")
    }
}