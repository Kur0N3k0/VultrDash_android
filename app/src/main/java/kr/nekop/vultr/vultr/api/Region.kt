package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Region (
    private val requester: APIRequest
) {
    fun listRegion(per_page: Int, cursor: String) : Regions? {
        var url = "/regions"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Regions>()) as Regions
    }

    fun listAvail(region_id: String) : RegionAvailablePlans? {
        val url = "/regions/$region_id/availability"
        return requester.get(url, RequestHelper.parser<RegionAvailablePlans>()) as RegionAvailablePlans
    }
}

data class Regions (
    val regions: List<RegionDetail>,
    val meta: Meta
)

data class RegionDetail (
    val id: String,
    val city: String,
    val country: String,
    val continent: String,
    val options: List<String>
)

data class RegionAvailablePlans (
    val available_plans: List<String>
)