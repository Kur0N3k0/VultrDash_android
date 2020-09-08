package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class Snapshot (
    private val requester: APIRequest
) {
    fun listSnapshot(per_page: Int, cursor: String) : Snapshots? {
        var url = "/snapshots"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Snapshots>()) as Snapshots
    }

    fun deleteSnapshot(snapshot_id: String) : Any? {
        val url = "/snapshots/$snapshot_id"
        return requester.delete(url, null)
    }

    fun getSnapshot(snapshot_id: String) : Snapshotx? {
        val url = "/snapshots/$snapshot_id"
        return requester.get(url, RequestHelper.parser<Snapshotx>()) as Snapshotx
    }

    fun updateSnapshot(snapshot_id: String, param: SnapshotUpdate) : Any? {
        val url = "/snapshots/$snapshot_id"
        return requester.put(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun createSnapshot(param: SnapshotCreate) : Snapshotx? {
        val url = "/snapshots"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<Snapshotx>()
        ) as Snapshotx
    }

    fun createSnapshotFromURL(param: SnapshotCreateFromURL) : Snapshotx? {
        val url = "/snapshot/create-from-url"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<Snapshotx>()
        ) as Snapshotx
    }
}

data class Snapshots (
    val snapshots: List<SnapshotDetail>,
    val meta: Meta
)

data class Snapshotx (
    val snapshot: SnapshotDetail
)

data class SnapshotDetail (
    val id: String,
    val date_created: String,
    val description: String,
    val size: UInt,
    val status: String,
    val OSID: Int,
    val APPID: Int
)

data class SnapshotUpdate (
    val description: String
)

data class SnapshotCreate (
    val instance_id: String,
    val description: String
)

data class SnapshotCreateFromURL (
    val url: String
)