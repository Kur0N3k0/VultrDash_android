package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.CallbackHelper

class Backups (
    val requestor: APIRequest
) {
    fun backups(id: String, instance_id: String, per_page: Int = 25, cursor: String = "") : Backupx {
        var url = "/backups"
        if(!id.equals("") || !instance_id.equals("") || per_page != 25 || !cursor.equals(""))
            url += "?id=$id&instance_id=$instance_id&per_page=$per_page&cursor=$cursor"
        return requestor.get(url, CallbackHelper.parser<Backupx>()) as Backupx
    }

    fun getBackup(id: String) : BackupsDetail {
        return requestor.get("/backups/$id", CallbackHelper.parser<BackupsDetail>()) as BackupsDetail
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