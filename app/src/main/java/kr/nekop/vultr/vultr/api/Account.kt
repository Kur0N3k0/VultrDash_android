package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Account(
    private val requester: APIRequest
) {
    fun info() : AccountInfo? {
        return requester.get(
            "/account",
            RequestHelper.parser<AccountInfo>()
        ) as AccountInfo
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