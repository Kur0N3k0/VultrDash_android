package kr.nekop.vultr.vultr.api

import com.squareup.moshi.Moshi
import kr.nekop.vultr.vultr.api.util.APIRequest
import okio.BufferedSource

class Account(
    val requestor: APIRequest
) {
    fun info() : AccountInfo? {
        var result: AccountInfo ?= null
        val callback = { resp: BufferedSource ->
            val moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(AccountInfo::class.java)
            result = adapter.fromJson(resp)
        }
        requestor.get("/account", callback)
        return result
    }
}

data class AccountInfo(
    val account: AccountInfoDetail
)

data class AccountInfoDetail (
    val name: String,
    val email: String,
    val acls: List<String>,
    val balance: Int,
    val pending_charges: Int,
    val last_payment_date: String,
    val last_payment_amount: Int
)