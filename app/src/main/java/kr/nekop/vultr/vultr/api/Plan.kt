package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Plan (
    private val requester: APIRequest
) {
    fun listPlans(type: String, per_page: Int, cursor: String) : Plans? {
        var url = "/plans"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Plans>()) as Plans
    }

    fun listBareMetalPlans(per_page: Int, cursor: String) : Plans? {
        var url = "/plans-metal"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Plans>()) as Plans
    }
}

data class Plans (
    val plans: List<PlanDetail>
)

data class PlanDetail (
    val id: String,
    val vcpu_count: Int,
    val ram: Int,
    val disk: Int,
    val bandwidth: Int,
    val monthly_cost: String,
    val type: String,
    val locations: List<String>
)