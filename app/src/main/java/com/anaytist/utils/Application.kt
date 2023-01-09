package com.anaytist.utils

import android.content.Context
import android.util.Log
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import java.util.HashMap

val appsflyer = AppsFlyerLib.getInstance()
val LOG_TAG = "Ana Log"

object TDHAnalytics {
    fun use(instance: Context, afDevKey: String, isDebug: Boolean) {
        // For debug - remove in production
        // For debug - remove in production
        appsflyer.setDebugLog(isDebug)

        appsflyer.init(afDevKey, null, instance)

        appsflyer.start(instance, afDevKey, object : AppsFlyerRequestListener {
            override fun onSuccess() {
                Log.d(LOG_TAG, "Launch sent successfully woi")
            }

            override fun onError(errorCode: Int, errorDesc: String) {
                Log.d(LOG_TAG, "Launch failed to be sent:\n" +
                        "Error code: " + errorCode + "\n"
                        + "Error description: " + errorDesc)
            }
        });
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
