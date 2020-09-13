package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Instance (
    private val requester: APIRequest
) {
    val create_backups = listOf("enabled", "disabled")
    val update_backups = listOf("enable", "disable")
    val backup_types = listOf("daily", "weekly", "monthly", "daily_alt_even", "daily_alt_odd")

    fun listInstance(per_page: Int = 25, cursor: String = "", filter: InstanceListFilter?) : Instances? {
        var url = "/instances"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"
        if(filter != null) {
            url += "&instance_id=${filter.instance_id}&tag=${filter.tag}&label=${filter.label}&main_ip=${filter.main_ip}"
        }

        return requester.get(url, RequestHelper.parser<Instances>()) as Instances
    }

    fun createInstance(param: InstanceCreate) : Instancex? {
        val url = "/instances"
        if(param.region.isEmpty())
            throw Exception("InstanceCreate::region must not be empty")
        if(param.plan.isEmpty())
            throw Exception("InstanceCreate::plan must not be empty")
        if(param.backups != null && create_backups.indexOf(param.backups) == -1)
            throw Exception("InstanceCreate::backups must be enabled or disabled")

        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<Instancex>()
        ) as Instancex
    }

    fun getInstance(instance_id: String) : Instancex? {
        val url = "/instances/$instance_id"
        return requester.get(url, RequestHelper.parser<Instancex>()) as Instancex
    }

    fun updateInstance(instance_id: String, param: InstanceUpdate?) : Any? {
        val url = "/instances/$instance_id"
        if(param != null) {
            if(update_backups.indexOf(param.backups) == -1)
                throw Exception("InstanceUpdate::backups must be enable or disable")

            return requester.patch(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.patch(url, "", null)
    }

    fun deleteInstance(instance_id: String) : Any? {
        val url = "/instances/$instance_id"
        return requester.delete(url, null)
    }

    fun haltInstances(param: InstanceIDs) : Any? {
        val url = "/instances/halt"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun rebootInstances(param: InstanceIDs) : Any? {
        val url = "/instances/reboot"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun startInstances(param: InstanceIDs) : Any? {
        val url = "/instances/start"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun startInstance(instance_id: String) : Any? {
        val url = "/instances/$instance_id/start"
        return requester.post(
            url,
            "",
            null
        )
    }

    fun rebootInstance(instance_id: String) : Any? {
        val url = "/instances/$instance_id/reboot"
        return requester.post(
            url,
            "",
            null
        )
    }

    fun reinstallInstance(instance_id: String, param: InstanceReinstall?) : Any? {
        val url = "/instances/$instance_id/start"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.post(url, "", null)
    }

    fun bandwidth(instance_id: String) : InstanceBandwidth? {
        val url = "/instances/$instance_id/bandwidth"
        return requester.get(url, RequestHelper.parser<InstanceBandwidth>()) as InstanceBandwidth
    }

    fun getNeighbors(instance_id: String) : InstanceNeighborList? {
        val url = "/instances/$instance_id/neighbors"
        return requester.get(url, RequestHelper.parser<InstanceNeighborList>()) as InstanceNeighborList
    }

    fun getPrivateNetwork(per_page: Int = 25, cursor: String = "", instance_id: String) : InstancePrivateNetworks? {
        var url = "/instances/$instance_id/private_networks"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"
        return requester.get(url, RequestHelper.parser<InstancePrivateNetworks>()) as InstancePrivateNetworks
    }

    fun getISOState(instance_id: String) : InstanceISOStatus? {
        val url = "/instances/$instance_id/iso"
        return requester.get(url, RequestHelper.parser<InstanceISOStatus>()) as InstanceISOStatus
    }

    fun attachISO(instance_id: String, param: InstanceISOID?) : Any? {
        val url = "/instances/$instance_id/iso/attach"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.post(url, "", null)
    }

    fun detachISO(instance_id: String) : Any? {
        val url = "/instances/$instance_id/iso/detach"
        return requester.post(
            url,
            "",
            null
        )
    }

    fun attachPrivateNetwork(instance_id: String, param: InstancePrivateNetworkID?) : Any? {
        val url = "/instances/$instance_id/private-networks/attach"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.post(url, "", null)
    }

    fun detachPrivateNetwork(instance_id: String, param: InstancePrivateNetworkID?) : Any? {
        val url = "/instances/$instance_id/private-networks/detach"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.post(url, "", null)
    }

    fun setBackupSchedule(instance_id: String, param: InstanceSetBackupSchedule) : InstanceBackupSchedule? {
        if(param.type.isEmpty())
            throw Exception("InstanceSetBackupSchedule::type must not be empty")
        if(backup_types.indexOf(param.type) == -1)
            throw Exception("InstanceSetBackupSchedule::type must be Instance::backup_types")
        if(param.dow != null && (param.dow < 1 || param.dow > 7))
            throw Exception("InstanceSetBackupSchedule::type must be 1 <= dow <= 7")

        val url = "/instances/$instance_id/backup-schedule"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<InstanceBackupSchedule>()
        ) as InstanceBackupSchedule
    }

    fun getBackupSchedule(instance_id: String) : InstanceBackupSchedule? {
        val url = "/instances/$instance_id/backup-schedule"
        return requester.get(url, RequestHelper.parser<InstanceBackupSchedule>()) as InstanceBackupSchedule
    }

    fun restoreInstance(instance_id: String, param: InstanceBackupID?) : Any? {
        val url = "/instances/$instance_id/restore"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.post(url, "", null)
    }

    fun getIPv4(per_page: Int = 25, cursor: String = "", instance_id: String) : InstanceIPv4s? {
        var url = "/instances/$instance_id/ipv4"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"
        return requester.get(url, RequestHelper.parser<InstanceIPv4s>()) as InstanceIPv4s
    }

    fun createIPv4(instance_id: String, param: InstanceCreateIPv4?) : InstanceIPv4? {
        val url = "/instances/$instance_id/ipv4"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                RequestHelper.parser<InstanceIPv4>()
            ) as InstanceIPv4
        }
        return requester.post(
            url,
            "",
            RequestHelper.parser<InstanceIPv4>()
        ) as InstanceIPv4
    }

    fun getIPv6(instance_id: String) : InstanceIPv6s? {
        val url = "/instances/$instance_id/ipv6"
        return requester.post(
            url,
            "",
            RequestHelper.parser<InstanceIPv6s>()
        ) as InstanceIPv6s
    }

    fun createReverseIPv6(instance_id: String, param: InstanceReverseIPv6) : Any? {
        if(param.ip.isEmpty())
            throw Exception("InstanceReverseIPv6::ip must not be empty")
        if(param.reverse.isEmpty())
            throw Exception("InstanceReverseIPv6::reverse must not be empty")

        val url = "/instances/$instance_id/ipv6/reverse"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun getReverseIPv6(instance_id: String) : InstanceReverseIPv6s? {
        val url = "/instances/$instance_id/ipv6/reverse"
        return requester.get(url, RequestHelper.parser<InstanceReverseIPv6s>()) as InstanceReverseIPv6s
    }

    fun createReverseIPv4(instance_id: String, param: InstanceReverseIPv4) : Any? {
        if(param.ip.isEmpty())
            throw Exception("InstanceReverseIPv4::ip must not be empty")
        if(param.reverse.isEmpty())
            throw Exception("InstanceReverseIPv4::reverse must not be empty")

        val url = "/instances/$instance_id/ipv4/reverse"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun getUserData(instance_id: String) : InstanceUserData? {
        val url = "/instances/$instance_id/user-data"
        return requester.get(url, RequestHelper.parser<InstanceUserData>()) as InstanceUserData
    }

    fun haltInstance(instance_id: String) : Any? {
        val url = "/instances/$instance_id/halt"
        return requester.post(
            url,
            "",
            null
        )
    }

    fun setDefaultReverseDNSEntry(instance_id: String, param: InstanceSetDefaultReverseDNSEntry?) : Any? {
        val url = "/instances/$instance_id/ipv4/reverse/default"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.post(url, "", null)
    }

    fun deleteIPv4(instance_id: String, ipv4: String) : Any? {
        val url = "/instances/$instance_id/ipv4/$ipv4"
        return requester.post(
            url,
            "",
            null
        )
    }

    fun deleteIPv6(instance_id: String, ipv6: String) : Any? {
        val url = "/instances/$instance_id/ipv6/$ipv6"
        return requester.post(
            url,
            "",
            null
        )
    }
}

data class InstanceListFilter (
    val instance_id: String,
    val tag: String,
    val label: String,
    val main_ip: String
)

data class Instances (
    val instances: List<InstanceDetail>,
    val meta: Meta
)

data class InstanceDetail (
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
    val app_id: Int,
    val firewall_group_id: String,
    val features: List<String>
)

data class InstanceCreate (
    val region: String,
    val plan: String,
    val os_id: Int?,
    val ipxe_chain_url: String?,
    val iso_id: String?,
    val script_id: Int?,
    val snapshot_id: String?,
    val enable_ipv6: Boolean?,
    val attach_private_network: List<String>?,
    val detach_private_network: List<String>?,
    val label: String?,
    val sshkey_id: String?,
    val backups: String?,
    val app_id: Int?,
    val user_data: String?,
    val ddos_protection: Boolean?,
    val activation_email: Boolean?,
    val hostname: String?,
    val tag: String?,
    val firewall_groupd_id: String?,
    val reserved_ipv4: String?
)

data class Instancex (
    val instance: List<InstanceDetail>
)

data class InstanceUpdate (
    val app_id: Int,
    val backups: String,
    val firewall_group_id: String,
    val enable_ipv6: Boolean,
    val os_id: String,
    val user_data: String,
    val tag: String,
    val plan: String,
    val ddos_protection: Boolean,
    val attach_private_network: List<String>,
    val detach_private_network: List<String>
)

data class InstanceIDs (
    val instance_ids: List<String>
)

data class InstanceReinstall (
    val hostname: String
)

data class InstanceNeighborList (
    val neighbors: List<String>
)

data class InstancePrivateNetworks (
    val private_networks: List<InstancePrivateNetwork>,
    val meta: Meta
)

data class InstancePrivateNetwork (
    val network_id: String,
    val mac_address: String,
    val ip_address: String
)

data class InstanceISOStatus (
    val iso_status: InstanceISOState
)

data class InstanceISOState (
    val state: String,
    val iso_id: String
)

data class InstanceISOID (
    val iso_id: String
)

data class InstancePrivateNetworkID (
    val network_id: String
)

data class InstanceSetBackupSchedule (
    val type: String,
    val hour: Int?,
    val dow: Int?,
    val dom: Int?
)

data class InstanceBackupSchedule (
    val backup_schedule: InstanceBackupScheduleDetail
)

data class InstanceBackupScheduleDetail (
    val enabled: Boolean,
    val type: String,
    val next_run_utc: String,
    val hour: Int,
    val dow: Int,
    val dom: Int
)

data class InstanceBackupID (
    val backup_id: String
)

data class InstanceIPv4s (
    val ipv4s: List<InstanceIPv4Detail>,
    val meta: Meta
)

data class InstanceIPv4 (
    val ipv4: InstanceIPv4Detail
)

data class InstanceIPv4Detail (
    val ip: String,
    val netmask: String,
    val gateway: String,
    val type: String,
    val reverse: String
)

data class InstanceIPv6s (
    val ipv6s: List<InstanceIPv6Detail>,
    val meta: Meta
)

data class InstanceIPv6Detail (
    val ip: String,
    val network: String,
    val network_size: Int,
    val type: String
)

data class InstanceCreateIPv4 (
    val reboot: Boolean
)

data class InstanceReverseIPv6 (
    val ip: String,
    val reverse: String
)

data class InstanceReverseIPv6s (
    val reverse_ipv6s: List<InstanceReverseIPv6>
)

data class InstanceReverseIPv4 (
    val ip: String,
    val reverse: String
)

data class InstanceUserData (
    val user_data: InstanceUserDataDetail
)

data class InstanceUserDataDetail (
    val data: String
)

data class InstanceSetDefaultReverseDNSEntry (
    val ip: String
)

data class InstanceBandwidth (
    val bandwidth: Map<String, InstanceBandwidthDetail>
)

data class InstanceBandwidthDetail (
    val incoming_bytes: Int,
    val outgoing_bytes: Int
)