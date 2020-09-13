package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Firewall (
    private val requester: APIRequest
) {
    val ip_types = listOf("v4", "v6")
    val protocols = listOf("ICMP", "TCP", "UDP", "GRE")
    val sources = listOf("", "cloudflare")

    fun listFirewall(per_page: Int = 25, cursor: String = "") : FirewallGroups? {
        var url = "/firewalls"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<FirewallGroups>()) as FirewallGroups
    }

    fun createGroup(param: FirewallCreateGroup?) : FirewallGroup? {
        val url = "/firewalls"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                RequestHelper.parser<FirewallGroup>()
            ) as FirewallGroup
        }
        return requester.post(
            url,
            "",
            RequestHelper.parser<FirewallGroup>()
        ) as FirewallGroup
    }

    fun getGroup(firewall_group_id: String) : FirewallGroup? {
        val url = "/firewalls/$firewall_group_id"
        return requester.get(url, RequestHelper.parser<FirewallGroup>()) as FirewallGroup
    }

    fun updateGroup(firewall_group_id: String, param: FirewallUpdateGroup) : Any? {
        val url = "/firewalls/$firewall_group_id"
        if(param.description.isEmpty())
            throw Exception("FirewallUpdateGroup::description must not be empty")

        return requester.put(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun deleteGroup(firewall_group_id: String) : Any? {
        val url = "/firewalls/$firewall_group_id"
        return requester.delete(url, null)
    }

    fun listRules(per_page: Int = 25, cursor: String = "", firewall_group_id: String) : FirewallRules? {
        var url = "/firewalls/$firewall_group_id/rules"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<FirewallRules>()) as FirewallRules
    }

    fun createRule(firewall_group_id: String, param: FirewallCreateRule) : FirewallRule? {
        if(param.ip_type.isEmpty())
            throw Exception("FirewallCreateRule::ip_type must not be empty")
        if(param.protocol.isEmpty())
            throw Exception("FirewallCreateRule::protocol must not be empty")
        if(param.subnet.isEmpty())
            throw Exception("FirewallCreateRule::subnet must not be empty")
        if(param.subnet_size < 0 || param.subnet_size > 32)
            throw Exception("FirewallCreateRule::subnet_size must be 1 < subnet_size <= 32")
        if(param.source != null && sources.indexOf(param.source) == -1)
            throw Exception("FirewallCreateRule::source must be empty or cloudflare")

        val url = "/firewalls/$firewall_group_id/rules"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<FirewallRule>()
        ) as FirewallRule
    }

    fun deleteRule(firewall_group_id: String, firewall_rule_id: String) : Any? {
        val url = "/firewalls/$firewall_group_id/rules/$firewall_rule_id"
        return requester.delete(url, null)
    }

    fun getRule(firewall_group_id: String, firewall_rule_id: String) : FirewallRule? {
        val url = "/firewalls/$firewall_group_id/rules/$firewall_rule_id"
        return requester.get(url, RequestHelper.parser<FirewallRule>()) as FirewallRule
    }
}

data class FirewallGroups (
    val firewall_groups: List<FirewallGroupDetail>,
    val meta: Meta
)

data class FirewallGroupDetail (
    val id: String,
    val description: String,
    val data_created: String,
    val data_modified: String,
    val instance_id: Int,
    val rule_count: Int,
    val max_rule_count: Int
)

data class FirewallCreateGroup (
    val description: String
)

data class FirewallGroup (
    val firewall_group: FirewallGroupDetail
)

data class FirewallUpdateGroup (
    val description: String
)

data class FirewallRules (
    val firewall_rules: List<FirewalLRulesDetail>,
    val meta: Meta
)

data class FirewalLRulesDetail (
    val id: Int,
    val ip_type: String,
    val action: String,
    val protocol: String,
    val port: String,
    val subnet: String,
    val subnet_size: Int,
    val source: String,
    val note: String
)

data class FirewallCreateRule (
    val ip_type: String,
    val action: String,
    val protocol: String,
    val port: String?,
    val subnet: String,
    val subnet_size: Int,
    val source: String?,
    val notes: String?
)

data class FirewallRule (
    val firewall_rule: FirewalLRulesDetail
)