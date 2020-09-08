package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class OperatingSystem (
    private val requester: APIRequest
) {
    fun listOS(per_page: Int, cursor: String) : OperatingSystemOSs? {
        var url = "/object-storage"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<OperatingSystemOSs>()) as OperatingSystemOSs
    }
}

data class OperatingSystemOSs (
    val os: List<OperatingSystemOSDetail>,
    val meta: Meta
)

data class OperatingSystemOSDetail (
    val id: Int,
    val name: String,
    val arch : String,
    val family: String
)