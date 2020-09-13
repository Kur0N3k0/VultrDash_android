package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class ISO (
    private val requester: APIRequest
) {
    fun listISO(per_page: Int = 25, cursor: String = "") : ISOs? {
        var url = "/iso"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Instances>()) as ISOs
    }

    fun create(param: ISOCreateURL) : ISOx? {
        val url = "/iso"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<ISOx>()
        ) as ISOx
    }

    fun getISO(iso_id: String) : ISOGetResult? {
        val url = "/iso/$iso_id"
        return requester.get(url, RequestHelper.parser<ISOGetResult>()) as ISOGetResult
    }

    fun deleteISO(iso_id: String) : Any? {
        val url = "/iso/$iso_id"
        return requester.delete(url, null)
    }

    fun listPublic() : ISOPublics? {
        val url = "/iso-public"
        return requester.get(url, RequestHelper.parser<ISOPublics>()) as ISOPublics
    }
}

data class ISOs (
    val isos: List<ISODetail>,
    val meta: Meta
)

data class ISODetail (
    val id: String,
    val date_created: String,
    val filename: String,
    val size: Int,
    val md5sum: String,
    val sha512sum: String,
    val status: String
)

data class ISOCreateURL (
    val url: String
)

data class ISOx (
    val iso: ISOxDetail
)

data class ISOxDetail (
    val id: String,
    val date_created: String,
    val filename: String,
    val status: String
)

data class ISOGetResult (
    val iso: ISODetail
)

data class ISOPublics (
    val public_isos: List<ISOPublicDetail>,
    val meta: Meta
)

data class ISOPublicDetail (
    val id: String,
    val name: String,
    val description: String,
    val md5sum: String
)