package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest

class VultrAPI(
    val apiKey: String,
    val requestor: APIRequest = APIRequest(apiKey)
) {
    val account: Account = Account(requestor)
    val application: Application = Application(requestor)
    val backups: Backups = Backups(requestor)

}