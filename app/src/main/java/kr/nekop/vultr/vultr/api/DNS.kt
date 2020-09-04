package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class DNS (
    private val requester: APIRequest
) {
    fun list(per_page: Int, cursor: String) : DNSDomains? {
        var url = "/domains"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<DNSDomains>()) as DNSDomains
    }

    fun createDomain(param: DNSDomainCreate) : DNSDomainDetail? {
        val url = "/domains"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<DNSDomainDetail>()
        ) as DNSDomainDetail
    }

    fun getDomain(dns_domain: String) : DNSDomain? {
        val url = "/domains/$dns_domain"
        return requester.get(url, RequestHelper.parser<DNSDomain>()) as DNSDomain
    }

    fun deleteDomain(dns_domain: String) : Any? {
        val url = "/domains/$dns_domain"
        return requester.delete(url, null)
    }

    fun updateDomain(dns_domain: String, param: DNSDomainUpdate) : Any? {
        if(!param.dns_sec.equals("enabled") && !param.dns_sec.equals("disabled"))
            throw Exception("DNSDomainUpdate::dns_sec is must be enabled or disabled")

        val url = "/domains/$dns_domain"
        return requester.put(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun getSOA(dns_domain: String) : DNSSOA? {
        val url = "/domains/$dns_domain/soa"
        return requester.get(url, RequestHelper.parser<DNSSOA>()) as DNSSOA
    }

    fun updateSOA(dns_domain: String, param: DNSSOADetail) : Any? {
        val url = "/domains/$dns_domain/soa"
        return requester.patch(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun getDNSSec(dns_domain: String) : DNSSec? {
        val url = "/domains/$dns_domain/dnssec"
        return requester.get(url, RequestHelper.parser<DNSSec>()) as DNSSec
    }


}

data class DNSDomains (
    val domains: List<DNSDomainDetail>,
    val meta: Meta
)

data class DNSDomainDetail (
    val domain: String,
    val date_created: String
)

data class DNSDomainCreate (
    val domain: String,
    val ip: String,
    val dns_sec: String
)

data class DNSDomain (
    val domain: DNSDomainDetail
)

data class DNSDomainUpdate (
    val dns_sec: String
)

data class DNSSOA (
    val dns_soa: DNSSOADetail
)

data class DNSSOADetail (
    val nsprimary: String,
    val email: String
)

data class DNSSec (
    val dns_sec: List<String>
)