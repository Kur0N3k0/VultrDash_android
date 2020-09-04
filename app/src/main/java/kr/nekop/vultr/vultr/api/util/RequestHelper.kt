package kr.nekop.vultr.vultr.api.util

import com.squareup.moshi.Moshi
import kr.nekop.vultr.vultr.api.BareMetalCreateInstance
import okio.BufferedSource

class RequestHelper {
    companion object {
        inline fun <reified T: Any> parser(): (BufferedSource) -> Any? {
            return { resp: BufferedSource ->
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(T::class.java)
                adapter.fromJson(resp)
            }
        }

        inline fun <reified T: Any> jsonize(param: T): String {
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(T::class.java)
            return adapter.toJson(param)
        }
    }

}