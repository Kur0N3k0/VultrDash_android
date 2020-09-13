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

    fun createDomain(param: DNSDomainCreate) : DNSDomainx? {
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
        val url = "/domains/$dns_domain/records"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<DNSRecord>()
        ) as DNSRecord
    }

    fun getRecords(dns_domain: String) : DNSRecords {
        val url = "/domains/$dns_domain/records"
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