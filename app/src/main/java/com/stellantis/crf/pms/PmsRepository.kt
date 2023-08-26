package com.stellantis.crf.pms

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "MaasRepository"

const val MAAS_SERVICE_ID = "MAAS"
const val MAAS_ACTIVATION_BODY = "{\"setdata\": \"{}\"}"

//private const val CRF_API_CORE_BASE_URL = "https://api.core.demo.dec.fcagcv.com"
private const val CRF_API_CORE_BASE_URL = "https://api.core.demo.dec.fcagcv.com"
private const val CRF_API_CORE_BASE_URL_NEW = "https://wr3ojbku24.execute-api.eu-west-3.amazonaws.com"
private const val CRF_API_CORE_USER = "openapi"
private const val CRF_API_CORE_PASSWORD = "sdljq3r83va"

//private const val REPLY_BACK_END_BASE_URL = "https://sdc1v6vlj3.execute-api.eu-central-1.amazonaws.com/dev"
private const val REPLY_BACK_END_BASE_URL_OLD =
    "https://wu6k2p38r0.execute-api.eu-central-1.amazonaws.com/test"
private const val REPLY_BACK_END_BASE_URL =
    "https://l9nh3wt9a4.execute-api.eu-central-1.amazonaws.com/test"

private val CRF_API_CORE_AUTH_HEADER = mapOf(
    "x-auth-token" to "OnlyForTests",
    basicAuthentication(CRF_API_CORE_USER, CRF_API_CORE_PASSWORD),
)
private val REPLY_BACK_END_AUTH_HEADER = mapOf("Authorization" to "OnlyForTests")

private const val OPEN_STREET_MAP_BASE_URL = "https://nominatim.openstreetmap.org"
private const val OPEN_STREET_MAP_QUERY_PARAM = "q"
private val OPEN_STREET_MAP_JSON_FORMAT_PARAM = "format" to "json"

object PmsRepository {

    private lateinit var volleyRequestQueue: RequestQueue

    fun initialize(context: Context) {
        volleyRequestQueue = Volley.newRequestQueue(context)
    }

    fun cancelRequests(tag: String) {
        volleyRequestQueue.cancelAll(tag)
    }

    private suspend fun enqueueNewRequest(
        url: String,
        method: Int,
        params: Map<String, String>? = null,
        headers: Map<String, String>? = null,
        body: String? = null,
        tag: String? = null,
    ): String? = suspendCoroutine { continuation ->
        val request = newVolleyRequest(
            url = url,
            method = method,
            params = params,
            headers = headers,
            tag = tag,
            body = body,
            then = { result ->
                when (result) {
                    is VolleyResult.Success -> {
                        Log.d(
                            TAG,
                            "GOT RESPONSE [${getMethodString(method)} ${url}] -> ${result.text}"
                        )
                        continuation.resume(result.text)
                    }

                    is VolleyResult.Error -> {
                        Log.e(
                            TAG,
                            "NETWORK ERROR [${getMethodString(method)} ${url}]: ${result.error.networkResponse?.data?.decodeToString()}"
                        )
                        continuation.resume(null)
                    }
                }
            }
        )
        // Log call details
        Log.d(TAG, "Calling -> ${getMethodString(method)} ${request.url}")
        headers?.let {
            Log.d(TAG, "HEADERS -> ${it.entries.joinToString { (key, value) -> "($key, $value)" }}")
        }
        body?.let { Log.d(TAG, "BODY -> $it") }
        // Enqueue request
        volleyRequestQueue.add(request)
    }

    private inline fun <reified T> String.deserializeAs(): T? {
        return try {
            this.deserialize<T>()
        } catch (e: Exception) {
            Log.e(TAG, "Unable to deserialize response as ${T::class.java.name}: $e")
            null
        }
    }

    /*******************/
    /** CRF CORE APIs **/
    /*******************/

    suspend fun getVehicleInfoOld(tag: String? = null): VehicleInfoOld? {
        return enqueueNewRequest(
            url = "$CRF_API_CORE_BASE_URL/dc/vehicles",
            method = Request.Method.GET,
            headers = CRF_API_CORE_AUTH_HEADER,
            tag = tag,
        )?.deserializeAs<VehicleInfoOld>()
    }




    /** Get Vehicle Info **/
    suspend fun getVehicleInfo(tag: String? = null): VehicleInfoNew? {
        return enqueueNewRequest(
            url = "$CRF_API_CORE_BASE_URL_NEW/vehicle_info/VR7ATTENTJL033368",
            method = Request.Method.GET,
            headers = CRF_API_CORE_AUTH_HEADER,
            tag = tag,
        )?.deserializeAs<VehicleInfoNew>()
    }

    suspend fun getUserInfo(tag: String? = null): UserInfo? {
        return enqueueNewRequest(
            url = "$CRF_API_CORE_BASE_URL_NEW/user/ed8c2494251143e2b59c11ad53efba2c",
            method = Request.Method.GET,
            headers = CRF_API_CORE_AUTH_HEADER,
            tag = tag,
        )?.deserializeAs<UserInfo>()
    }

    suspend fun getNotification(tag: String? = null): NotificationsNew? {
        return enqueueNewRequest(
            url = "$CRF_API_CORE_BASE_URL_NEW/notifications/ed8c2494251143e2b59c11ad53efba2c/VR7ATTENTJL033368",
            method = Request.Method.GET,
            headers = CRF_API_CORE_AUTH_HEADER,
            tag = tag,
        )?.deserializeAs<NotificationsNew>()
    }
}

const val OPEN_COMMAND = "OPEN_COMMAND"
const val CLOSE_COMMAND = "CLOSE_COMMAND"

/** Utility **/

private fun getMethodString(method: Int): String {
    return when (method) {
        0 -> "GET"
        1 -> "POST"
        2 -> "PUT"
        3 -> "DELETE"
        4 -> "HEAD"
        5 -> "OPTIONS"
        6 -> "TRACE"
        7 -> "PATCH"
        else -> "UNKNOWN"
    }
}

