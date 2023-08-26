package com.stellantis.crf.pms

import android.util.Base64
import android.util.Log
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import java.net.URLEncoder

fun newVolleyRequest(
    url: String,
    method: Int = Request.Method.GET,
    params: Map<String, String>? = null,
    headers: Map<String, String>? = null,
    body: String? = null,
    tag: String? = null,
    then: (VolleyResult) -> Unit,
): StringRequest {
    val encodedUrl = StringBuilder(url)
    if (params != null && params.isNotEmpty()) {
        encodedUrl.append("?")
        encodedUrl.append(
            params.entries.joinToString(separator = "&") { (key, value) ->
                "$key=${
                    URLEncoder.encode(
                        value,
                        Charsets.UTF_8.name()
                    )
                }"
            }
        )
    }
    val request = object : StringRequest(method, encodedUrl.toString(), { response ->
        then.invoke(VolleyResult.Success(response))
    }, { error ->
        Log.e("VolleyUtils", "ERROR: $error")
        then.invoke(VolleyResult.Error(error))
    }) {
        override fun getParams(): MutableMap<String, String>? {
            return params?.toMutableMap() ?: super.getParams()
        }

        override fun getHeaders(): MutableMap<String, String> {
            return headers?.toMutableMap() ?: super.getHeaders()
        }

        override fun getBody(): ByteArray {
            return body?.toByteArray(Charsets.UTF_8) ?: super.getBody()
        }
    }
    tag?.let { request.tag = it }
    return request
}

sealed class VolleyResult {
    class Success(
        val text: String,
    ) : VolleyResult()

    class Error(
        val error: VolleyError,
    ) : VolleyResult()
}

fun basicAuthentication(user: String, password: String): Pair<String, String> {
    return "Authorization" to "Basic ${
        Base64.encodeToString(
            "$user:$password".toByteArray(),
            Base64.DEFAULT
        )
    }"
}