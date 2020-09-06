package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class LoadBalancer (
    private val requester: APIRequest
) {
    fun listLoadBalancers(per_page: Int, cursor: String) : LoadBalancers? {
        var url = "/load-balancers"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<LoadBalancers>()) as LoadBalancers
    }

    fun createLoadBalancer(param: LoadBalancerCreate) : LoadBalancerx? {
        val url = "/load-balancers"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<LoadBalancerx>()
        ) as LoadBalancerx
    }

    fun getLoadBalancer(load_balancer_id: String) : LoadBalancerx? {
        val url = "/load-balancers/$load_balancer_id"
        return requester.get(url, RequestHelper.parser<LoadBalancerx>()) as LoadBalancerx
    }

    fun updateLoadBalancer(load_balancer_id: String, param: LoadBalancerUpdate) : Any? {
        val url = "/load-balancers/$load_balancer_id"
        return requester.patch(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun deleteLoadBalancer(load_balancer_id: String) : Any? {
        val url = "/load-balancers/$load_balancer_id"
        return requester.delete(url, null)
    }

    fun listForwardingRules(load_balancer_id: String) : LoadBalancerForwardRules? {
        val url = "/load-balancers/$load_balancer_id/forwarding-rules"
        return requester.get(url, RequestHelper.parser<LoadBalancerForwardRules>()) as LoadBalancerForwardRules
    }

    fun createForwardingRules(load_balancer_id: String, param: LoadBalancerCreateForwardRule) : LoadBalancerCreateForwardRule? {
        val url = "/load-balancers/$load_balancer_id/forwarding-rules"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<LoadBalancerCreateForwardRule>()
        ) as LoadBalancerCreateForwardRule
    }

    fun getForwardingRule(load_balancer_id: String, forwarding_rule_id: String) : LoadBalancerForwardRule? {
        val url = "/load-balancers/$load_balancer_id/forwarding-rules/$forwarding_rule_id"
        return requester.get(url, RequestHelper.parser<LoadBalancerForwardRule>()) as LoadBalancerForwardRule
    }

    fun deleteForwardingRule(load_balancer_id: String, forwarding_rule_id: String) : Any? {
        val url = "/load-balancers/$load_balancer_id/forwarding-rules/$forwarding_rule_id"
        return requester.delete(url, null)
    }
}

data class LoadBalancers (
    val load_balancers: List<LoadBalancerDetail>,
    val meta: Meta
)

data class LoadBalancerDetail (
    val id: String,
    val date_created: String,
    val region: String,
    val label: String,
    val status: String,
    val ipv4: String,
    val ipv6: String,
    val generic_info: LoadBalancerGenericInfo,
    val health_check: LoadBalancerHealthCheck,
    val has_ssl: Boolean,
    val forwarding_rules: LoadBalancerForwardRule,
    val instances: List<String>
)

data class LoadBalancerGenericInfo (
    val balancing_algorithm: String,
    val ssl_redirect: Boolean,
    val sticky_sessions: LoadBalancerStickySession
)

data class LoadBalancerStickySession (
    val cookie_name: String
)

data class LoadBalancerHealthCheck (
    val protocol: String,
    val port: Int,
    val path: String,
    val check_interval: Int,
    val response_timeout: Int,
    val unhealthy_threshold: Int,
    val healthy_threshold: Int
)

data class LoadBalancerForwardRuleDetail (
    val id: String,
    val frontend_protocol: String,
    val frontend_port: Int,
    val backend_protocol: String,
    val backend_port: Int
)

data class LoadBalancerCreate (
    val region: String,
    val balancing_algorithm: String,
    val ssl_redirect: Boolean,
    val proxy_protocol: String,
    val health_check: LoadBalancerHealthCheck,
    val forwarding_rules: List<LoadBalancerCreateForwardRule>,
    val sticky_sessions: LoadBalancerStickySession,
    val ssl: LoadBalancerSSL,
    val label: String,
    val instance: List<String>
)

data class LoadBalancerx (
    val load_balancer: LoadBalancerDetail
)

data class LoadBalancerSSL (
    val reserved: String
)

data class LoadBalancerUpdate (
    val ssl: LoadBalancerSSL,
    val sticky_sessions: LoadBalancerStickySession,
    val forwarding_rules: LoadBalancerForwardRuleDetail,
    val health_check: LoadBalancerHealthCheck,
    val proxy_protocol: String,
    val ssl_redirect: Boolean,
    val balancing_algorithm: String,
    val instance: List<String>
)

data class LoadBalancerForwardRules (
    val forwarding_rules: List<LoadBalancerForwardRuleDetail>,
    val meta: Meta
)

data class LoadBalancerCreateForwardRule (
    val frontend_protocol: String,
    val frontend_port: Int,
    val backend_protocol: String,
    val backend_port: Int
)

data class LoadBalancerForwardRule (
    val forwarding_rule: LoadBalancerForwardRuleDetail
)