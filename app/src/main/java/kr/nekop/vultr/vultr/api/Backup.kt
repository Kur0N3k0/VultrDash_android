package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Backup (
    private val requester: APIRequest
) {
    fun listBackup(per_page: Int = 25, cursor: String = "", id: String, instance_id: String) : Backups {
        var url = "/backups"
        if(id != "" || instance_id != "" || per_page != 25 || cursor != "")
            url += "?id=$id&instance_id=$instance_id&per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Backups>()) as Backups
    }

    fun getBackup(id: String) : Backupx {
        return requester.get("/backups/$id", RequestHelper.parser<Backupx>()) as Backupx
    }
}

data class Backups (
    val backups: List<BackupDetail>,
    val meta: List<Meta>
)

data class Backupx (
    val backup: BackupDetail
)

data class BackupDetail (
    val id: String,
    val date_created: String,
    val description: String,
    val size: Int,
    val status: String
)