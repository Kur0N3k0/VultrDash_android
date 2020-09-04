package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Application (
    private val requester: APIRequest
) {
    fun applications(per_page: Int = 25, cursor: String = "") : Applications? {
        var url = "/applications"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Applications>()) as Applications
    }
}

data class Applications (
    val applications: List<ApplicationsDetail>
)

data class ApplicationsDetail (
    val id: Int,
    val name: String,
    val short_name: String,
    val deploy_name: String
)

data class Meta (
    val total: Int,
    val links: List<MetaLinks>
)

data class MetaLinks (
    val next: String,
    val prev: String
)