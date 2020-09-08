package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class PrivateNetwork (
    private val requester: APIRequest
) {
    fun listNetworks(per_page: Int, cursor: String) : PrivateNetworks? {
        var url = "/private-networks"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<PrivateNetworks>()) as PrivateNetworks
    }

    fun getNetwork(network_id: String) : PrivateNetworkx? {
        val url = "/private-networks/$network_id"
        return requester.get(url, RequestHelper.parser<PrivateNetworkx>()) as PrivateNetworkx
    }

    fun deleteNetwork(network_id: String) : Any? {
        val url = "/private-networks/$network_id"
        return requester.delete(url, null)
    }

    fun updateNetwork(network_id: String, param: PrivateNetworkUpdate) : Any? {
        val url = "/private-networks/$network_id"
        return requester.put(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun createNetwork(param: PrivateNetworkCreate) : PrivateNetworkx? {
        val url = "/private-networks"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<PrivateNetworkx>()
        ) as PrivateNetworkx
    }
}

data class PrivateNetworks (
    val networks: List<PrivateNetworkDetail>,
    val meta: Meta
)

data class PrivateNetworkx (
    val network: PrivateNetworkDetail
)

data class PrivateNetworkDetail (
    val id: String,
    val date_created: String,
    val region: String,
    val description: String,
    val v4_subnet: String,
    val v4_subnet_mask: Int
)

data class PrivateNetworkUpdate (
    val description: String
)

data class PrivateNetworkCreate (
    val region: String,
    val description: String,
    val v4_subnet: String,
    val v4_subnet_mask: Int
)