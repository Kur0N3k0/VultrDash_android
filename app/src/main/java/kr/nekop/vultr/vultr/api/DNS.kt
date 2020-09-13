package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class DNS (
    private val requester: APIRequest
) {
    val types = listOf(
        "A", "AAAA", "CNAME", "NS", "MX", "SRV", "TXT", "CAA", "SSHFP"
    )

    fun listDNS(per_page: Int = 25, cursor: String = "") : DNSDomains? {
        var url = "/domains"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<DNSDomains>()) as DNSDomains
    }

    fun createDomain(param: DNSDomainCreate) : DNSDomainx? {
        if(param.domain.isEmpty())
            throw Exception("DNSDOmainCreate::domain must not be empty")

        val url = "/domains"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<DNSDomainx>()
        ) as DNSDomainx
    }

    fun getDomain(dns_domain: String) : DNSDomainx? {
        val url = "/domains/$dns_domain"
        return requester.get(url, RequestHelper.parser<DNSDomainx>()) as DNSDomainx
    }

    fun deleteDomain(dns_domain: String) : Any? {
        val url = "/domains/$dns_domain"
        return requester.delete(url, null)
    }

    fun updateDomain(dns_domain: String, param: DNSDomainUpdate) : Any? {
        if(param.dns_sec != "enabled" && param.dns_sec != "disabled")
            throw Exception("DNSDomainUpdate::dns_sec must be enabled or disabled")

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

    fun updateSOA(dns_domain: String, param: DNSSOADetail?) : Any? {
        val url = "/domains/$dns_domain/soa"
        if(param != null) {
            return requester.patch(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.patch(url, "", null)
    }

    fun getDNSSec(dns_domain: String) : DNSSec? {
        val url = "/domains/$dns_domain/dnssec"
        return requester.get(url, RequestHelper.parser<DNSSec>()) as DNSSec
    }

    fun createRecord(dns_domain: String, param: DNSCreateRecord) : DNSRecord? {
        if(param.name.isEmpty())
            throw Exception("DNSCreateRecord::name must not be empty")
        if(param.type.isEmpty())
            throw Exception("DNSCreateRecord::type must not be empty")
        if(param.data.isEmpty())
            throw Exception("DNSCreateRecord::data must not be empty")

        if(types.indexOf(param.type) == -1)
            throw Exception("DNSCreateRecord::type must in DNS::types")

        val url = "/domains/$dns_domain/records"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<DNSRecord>()
        ) as DNSRecord
    }

    fun getRecords(per_page: Int = 25, cursor: String = "", dns_domain: String) : DNSRecords {
        var url = "/domains/$dns_domain/records"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<DNSRecords>()) as DNSRecords
    }

    fun getRecord(dns_domain: String, record_id: String) : DNSRecord {
        val url = "/domains/$dns_domain/records/$record_id"
        return requester.get(url, RequestHelper.parser<DNSRecord>()) as DNSRecord
    }

    fun updateRecord(dns_domain: String, record_id: String, param: DNSUpdateRecord?) : Any? {
        val url = "/domains/$dns_domain/records/$record_id"
        if(param != null) {
            return requester.patch(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.patch(url, "", null)
    }

    fun deleteRecord(dns_domain: String, record_id: String) : Any? {
        val url = "/domains/$dns_domain/records/$record_id"
        return requester.delete(url, null)
    }
}

data class DNSDomains (
    val domains: List<DNSDomainDetail>,
    val meta: Meta
)

data class DNSDomainx (
    val domain: DNSDomainDetail
)

data class DNSDomainDetail (
    val domain: String,
    val date_created: String
)

data class DNSDomainCreate (
    val domain: String,
    val ip: String?,
    val dns_sec: String?
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

data class DNSCreateRecord (
    val name: String,
    val type: String,
    val data: String,
    val ttl: Int?,
    val priority: Int?
)

data class DNSRecord (
    val record: DNSRecordDetail
)

data class DNSRecordDetail (
    val id: String,
    val type: String,
    val name: String,
    val data: String,
    val priority: Int,
    val ttl: Int
)

data class DNSRecords (
    val records: List<DNSRecordDetail>,
    val meta: Meta
)

data class DNSUpdateRecord (
    val name: String?,
    val data: String?,
    val ttl: Int?,
    val priority: Int?
)