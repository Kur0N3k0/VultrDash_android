package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class ReservedIP (
    private val requester: APIRequest
) {
    fun listIPs(per_page: Int, cursor: String) : ReservedIPs? {
        var url = "/reserved-ips"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<ReservedIPs>()) as ReservedIPs
    }

    fun getIP(reserved_ip: String) : ReservedIPx? {
        val url = "/reserved-ips/$reserved_ip"
        return requester.get(url, RequestHelper.parser<ReservedIPx>()) as ReservedIPx
    }

    fun deleteIP(reserved_ip: String) : Any? {
        val url = "/reserved-ips/$reserved_ip"
        return requester.delete(url, null)
    }

    fun createIP(param: ReservedIPCreate) : ReservedIPx? {
        val url = "/reserved-ips"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<ReservedIPx>()
        ) as ReservedIPx
    }

    fun attachIP(reserved_ip: String, param: ReservedIPAttach) : Any? {
        val url = "/reserved-ips/$reserved_ip/attach"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun detachIP(reserved_ip: String) : Any? {
        val url = "/reserved-ips/$reserved_ip/detach"
        return requester.post(
            url,
            "",
            null
        )
    }

    fun convertInstanceIPtoReservedIP(param: ReservedIPConvert) : ReservedIPx? {
        val url = "/reserved-ips/convert"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<ReservedIPx>()
        ) as ReservedIPx
    }
}

data class ReservedIPs (
    val reserved_ips: List<ReservedIP>,
    val meta: Meta
)

data class ReservedIPx (
    val reserved_ip: ReservedIPDetail
)

data class ReservedIPDetail (
    val id: String,
    val region: String,
    val ip_type: String,
    val subnet: String,
    val subnet_size: Int,
    val label: String,
    val instance_id: String
)

data class ReservedIPCreate (
    val region: String,
    val ip_type: String,
    val label: String
)

data class ReservedIPAttach (
    val instance_id: String
)

data class ReservedIPConvert (
    val ip_address: String,
    val label: String
)