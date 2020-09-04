package kr.nekop.vultr.vultr.api.util

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.BufferedSource
import java.io.IOException

class APIRequest (
    val apiKey: String,
    val baseUrl: String = "http://api.vultr.com/v2"
) {
    fun get(url: String, callback: (resp: BufferedSource) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(baseUrl + url)
            .header("Authorization", "Bearer $apiKey")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return callback(response.body!!.source())
        }
    }

    fun post(url: String, parameters: HashMap<String, String>, callback: (resp: BufferedSource) -> Unit) {
        val client = OkHttpClient()

        val builder = FormBody.Builder()
        val it = parameters.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next() as Map.Entry<*, *>
            builder.add(pair.key.toString(), pair.value.toString())
        }

        val body = builder.build()
        val request = Request.Builder()
            .url(baseUrl + url)
            .header("Authorization", "Bearer $apiKey")
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            return callback(response.body!!.source())
        }
    }
}