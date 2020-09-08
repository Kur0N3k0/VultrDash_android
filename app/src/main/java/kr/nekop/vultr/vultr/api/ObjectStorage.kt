package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class ObjectStorage (
    private val requester: APIRequest
) {
    fun listStorage(per_page: Int, cursor: String) : ObjectStorages? {
        var url = "/object-storage"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<ObjectStorages>()) as ObjectStorages
    }

    fun createStorage(param: ObjectStorageCreate) : ObjectStoragex? {
        val url = "/object-storage"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<ObjectStoragex>()
        ) as ObjectStoragex
    }

    fun getStorage(object_storage_id: String) : ObjectStoragex? {
        val url = "/object-storage/$object_storage_id"
        return requester.get(url, RequestHelper.parser<ObjectStoragex>()) as ObjectStoragex
    }

    fun deleteStorage(object_storage_id: String): Any? {
        val url = "/object-storage/$object_storage_id"
        return requester.delete(url, null)
    }

    fun updateStorage(object_storage_id: String, param: ObjectStorageUpdate) : Any? {
        val url = "/object-storage/$object_storage_id"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<ObjectStorageUpdate>()
        ) as ObjectStorageUpdate
    }

    fun regenerateStorage(object_storage_id: String) : ObjectStorageRegenerate? {
        val url = "/object-storage/$object_storage_id/regenerate-keys"
        return requester.post(
            url,
            "",
            RequestHelper.parser<ObjectStorageRegenerate>()
        ) as ObjectStorageRegenerate
    }

    fun getClusters() : ObjectStorageClusters? {
        val url = "/object-storage/cluteers"
        return requester.get(url, RequestHelper.parser<ObjectStorageClusters>()) as ObjectStorageClusters
    }

}

data class ObjectStorages (
    val object_storages: List<ObjectStorageDetail>,
    val meta: Meta
)

data class ObjectStorageDetail (
    val id: String,
    val date_created: String,
    val cluster_id: String,
    val region: String,
    val label: String,
    val status: String,
    val s3_hostname: String,
    val s3_access_key: String,
    val s3_secret_key: String
)

data class ObjectStorageCreate (
    val cluster_id: String,
    val label: String
)

data class ObjectStoragex (
    val object_storage: ObjectStorageDetail
)

data class ObjectStorageUpdate (
    val label: String
)

data class ObjectStorageRegenerate (
    val s3_credential: ObjectStorageCredentials
)

data class ObjectStorageCredentials (
    val s3_hostname: String,
    val s3_access_key: String,
    val s3_secret_key: String
)

data class ObjectStorageClusters (
    val clusters: List<ObjectStorageClusterDetail>,
    val meta: Meta
)

data class ObjectStorageClusterDetail (
    val id: String,
    val region: String,
    val hostname: String,
    val deploy: String
)