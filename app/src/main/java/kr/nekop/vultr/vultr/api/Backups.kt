package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Backups (
    private val requester: APIRequest
) {
    fun backups(id: String, instance_id: String, per_page: Int = 25, cursor: String = "") : Backupx {
        var url = "/backups"
        if(!id.equals("") || !instance_id.equals("") || per_page != 25 || !cursor.equals(""))
            url += "?id=$id&instance_id=$instance_id&per_page=$per_page&cursor=$cursor"
        return requester.get(url, RequestHelper.parser<Backupx>()) as Backupx
    }

    fun getBackup(id: String) : BackupsDetail {
        return requester.get("/backups/$id", RequestHelper.parser<BackupsDetail>()) as BackupsDetail
    }
}

data class Backupx (
    val backups: List<BackupsDetail>,
    val meta: List<Meta>
)

data class BackupsDetail (
    val id: String,
    val date_created: String,
    val description: String,
    val size: Int,
    val status: String
)