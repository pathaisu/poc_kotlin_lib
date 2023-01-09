package com.anaytist.utils

import android.content.Context
import android.util.Log
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import java.util.HashMap

var appsflyer = AppsFlyerLib.getInstance()
var LOG_TAG = "Ana Log"
var appFlyerKey = ""
var tools = arrayOf<String>()

fun AppsFlyerAnalyticsProvider(instance: Context, afDevKey: String, isDebug: Boolean): String {
    // For debug - remove in production
    // For debug - remove in production
    appsflyer.setDebugLog(isDebug)

    appsflyer.init(afDevKey, null, instance)

    appFlyerKey = afDevKey

    return "appFlyer"
}

object TDHAnalytics {
    fun use(tool: String) {
        Log.d(LOG_TAG, "Platform $tool")
//        tools.set(0, tool);
    }

    fun start(instance: Context) {
//        if (tools[0] === "appFlyer") {
            appsflyer.start(instance, appFlyerKey, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Log.d(LOG_TAG, "Launch sent successfully woi")
                }

                override fun onError(errorCode: Int, errorDesc: String) {
                    Log.d(LOG_TAG, "Launch failed to be sent:\n" +
                            "Error code: " + errorCode + "\n"
                            + "Error description: " + errorDesc)
                }
            });
//        }
    }

    fun logEvent(instance: Context) {
        val eventValues = HashMap<String, Any>()
        eventValues.put(AFInAppEventParameterName.PRICE, 1234.56)
        eventValues.put(AFInAppEventParameterName.CONTENT_ID,"1234567")

        appsflyer.logEvent(instance ,
            AFInAppEventType.ADD_TO_CART , eventValues, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Log.d(LOG_TAG, "Event sent successfully wa")
                }
                override fun onError(errorCode: Int, errorDesc: String) {
                    Log.d(LOG_TAG, "Event failed to be sent:\n" +
                            "Error code: " + errorCode + "\n"
                            + "Error description: " + errorDesc)
                }
            })

        Log.d("Ana Logger", "---- > > > new ver na")
    }
}
