package kr.nekop.vultr.vultr.api.util

import com.squareup.moshi.Moshi
import okio.BufferedSource

class CallbackHelper {
    companion object {
        inline fun <reified T: Any> parser(): (BufferedSource) -> Unit {
            return { resp: BufferedSource ->
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(T::class.java)
                adapter.fromJson(resp)
            }
        }
    }

}