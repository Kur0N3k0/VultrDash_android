package kr.nekop.vultr.vultr.api.util

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSource
import org.json.JSONObject
import java.io.IOException

class APIRequest (
    private val apiKey: String,
    private val baseUrl: String = "http://api.vultr.com/v2"
) {
    fun get(url: String, callback: ((resp: BufferedSource) -> Any?)?): Any? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(baseUrl + url)
            .header("Authorization", "Bearer $apiKey")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return callback?.invoke(response.body!!.source())
        }
    }

    fun post(url: String, parameters: String, callback: ((resp: BufferedSource) -> Any?)?) : Any? {
        val client = OkHttpClient()

        val JSON = "application/json; charset=utf-8".toMediaType()
        val body = parameters
        /*
        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<*, *>
            builder.add(pair.key.toString(), pair.value.toString())
        }
        val body = builder.build()
        */

        val request = Request.Builder()
            .url(baseUrl + url)
            .header("Authorization", "Bearer $apiKey")
            .post(body.toRequestBody(JSON))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return callback?.invoke(response.body!!.source())
        }
    }

    fun put(url: String, parameters: String, callback: ((resp: BufferedSource) -> Any?)?): Any? {
        val client = OkHttpClient()

        val JSON = "application/json; charset=utf-8".toMediaType()
        val body = parameters

        val request = Request.Builder()
            .url(baseUrl + url)
            .header("Authorization", "Bearer $apiKey")
            .put(body.toRequestBody(JSON))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return callback?.invoke(response.body!!.source())
        }
    }

    fun patch(url: String, parameters: String, callback: ((resp: BufferedSource) -> Any?)?): Any? {
        val client = OkHttpClient()

        val JSON = "application/json; charset=utf-8".toMediaType()
        val body = parameters

        val request = Request.Builder()
            .url(baseUrl + url)
            .header("Authorization", "Bearer $apiKey")
            .patch(body.toRequestBody(JSON))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return callback?.invoke(response.body!!.source())
        }
    }

    fun delete(url: String, callback: ((resp: BufferedSource) -> Any?)?): Any? {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(baseUrl + url)
            .header("Authorization", "Bearer $apiKey")
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return callback?.invoke(response.body!!.source())
        }
    }
}