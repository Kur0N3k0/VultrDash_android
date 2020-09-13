package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class BareMetal (
    private val requester: APIRequest
) {
    fun listInstance(per_page: Int = 25, cursor: String = "") : BareMetals? {
        var url = "/bare-metals"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"
        return requester.get(url, RequestHelper.parser<BareMetals>()) as BareMetals
    }

    fun createInstance(param: BareMetalCreateInstance) : BareMetalx? {
        val url = "/bare-metals"
        if (param.region.isEmpty() || param.plan.isEmpty())
            throw Exception("BareMetalCreateInstance::region or plan must not be empty")

        return requester.post(url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<BareMetalx>()
        ) as BareMetalx
    }

    fun getInstance(baremetal_id: String) : BareMetalx? {
        val url = "/bare-metals/$baremetal_id"
        return requester.get(url, RequestHelper.parser<BareMetalx>()) as BareMetalx
    }

    fun updateInstance(baremetal_id: String, param: BareMetalUpdate?) : Any? {
        val url = "/bare-metals/$baremetal_id"
        if(param != null)
            return requester.patch(url, RequestHelper.jsonize(param), null)
        return requester.patch(url, "", null)
    }

    fun deleteInstance(baremetal_id: String) : Any? {
        val url = "/bare-metals/$baremetal_id"
        return requester.delete(url, null)
    }

    fun getIPv4(baremetal_id: String) : BareMetalIPv4? {
        val url = "/bare-metals/$baremetal_id/ipv4"
        return requester.get(url, RequestHelper.parser<BareMetalIPv4>()) as BareMetalIPv4
    }

    fun getIPv6(baremetal_id: String) : BareMetalIPv6? {
        val url = "/bare-metals/$baremetal_id/ipv6"
        return requester.get(url, RequestHelper.parser<BareMetalIPv6>()) as BareMetalIPv6
    }

    fun reboot(baremetal_id: String) : Any? {
        val url = "/bare-metals/$baremetal_id/reboot"
        return requester.post(url, "", null)
    }

    fun reboot(baremetal_ids: BareMetalIDs) : Any? {
        val url = "/bare-metals/reboot"
        return requester.post(url,
            RequestHelper.jsonize(baremetal_ids),
            null
        )
    }

    fun reinstall(baremetal_id: String) : Any? {
        val url = "/bare-metals/$baremetal_id/reinstall"
        return requester.post(url, "", null)
    }

    fun halt(baremetal_id: String) : Any? {
        val url = "/bare-metals/$baremetal_id/halt"
        return requester.post(url, "", null)
    }

    fun halt(baremetal_ids: BareMetalIDs) : Any? {
        val url = "/bare-metals/halt"
        return requester.post(url,
            RequestHelper.jsonize(baremetal_ids),
            null
        )
    }

    fun start(baremetal_ids: List<String>) : Any? {
        val url = "/bare-metals/start"
        return requester.post(url,
            RequestHelper.jsonize(baremetal_ids),
            null
        ) as BareMetalIDs
    }

    fun userData(baremetal_id: String) : BareMetalUserData {
        val url = "/bare-metals/$baremetal_id/user-data"
        return requester.get(url, RequestHelper.parser<BareMetalUserData>()) as BareMetalUserData
    }

    fun bandwidth(baremetal_id: String): Any? {
        val url = "/bare-metals/$baremetal_id/bandwidth"
        return requester.get(url, RequestHelper.parser<BareMetalBandwidth>()) as BareMetalBandwidth
    }
}

data class BareMetals (
    val bare_metals: List<BareMetalDetail>,
    val meta: Meta
)

data class BareMetalDetail (
    val id: String,
    val os: String,
    val ram: String,
    val disk: String,
    val main_ip: String,
    val cpu_count: Int,
    val region: String,
    val default_password: String,
    val date_created: String,
    val status: String,
    val netmask_v4: String,
    val gateway_v4: String,
    val plan: String,
    val v6_network: String,
    val v6_main_ip: String,
    val v6_subnet: Int,
    val label: String,
    val tag: String,
    val os_id: Int,
    val app_id: Int
)

data class BareMetalx (
    val bare_metal: BareMetalDetail
)

data class BareMetalx2 (
    val baremetal: BareMetalDetail
)

data class BareMetalCreateInstance (
    val region: String,
    val plan: String,
    val script_id: Int?,
    val enable_ipv6: Boolean?,
    val sshkey_id: String?,
    val user_data: String?,
    val label: String?,
    val notify_active: Boolean?,
    val hostname: String?,
    val tag: String?,
    val reserved_ipv4: String?,
    val os_id: Int?,
    val snapshot_id: Int?,
    val app_id: Int?
)

data class BareMetalUpdate (
    val user_data: String?,
    val label: String?,
    val tag: String?,
    val os_id: Int?,
    val app_id: Int?
)

data class BareMetalIPv4 (
    val ipv4s: List<BareMetalIPv4Detail>,
    val meta: Meta
)

data class BareMetalIPv4Detail (
    val ip: String,
    val netmask: String,
    val gateway: String,
    val type: String,
    val reverse: String
)

data class BareMetalIPv6 (
    val ipv6s: List<BareMetalIPv4Detail>,
    val meta: Meta
)

data class BareMetalIPv6Detail (
    val ip: String,
    val network: String,
    val network_size: Int,
    val type: String
)

data class BareMetalIDs (
    val baremetal_ids: List<String>
)

data class BareMetalUserData (
    val baremetal_ids: BareMetalUserDataDetail
)

data class BareMetalUserDataDetail (
    val data: String
)

data class BareMetalBandwidth (
    val bandwidth: Map<String, BareMetalBandwidthDetail>
)

data class BareMetalBandwidthDetail (
    val incoming_bytes: Int,
    val outgoing_bytes: Int
)