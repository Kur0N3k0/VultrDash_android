package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class BlockStorage (
    private val requester: APIRequest
){
    fun blocks(per_page: Int = 25, cursor: String = "") : BlockStorageBlocks? {
        var url = "/blocks"
        if(per_page != 25 || cursor != "")
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<BlockStorageBlocks>()) as BlockStorageBlocks
    }

    fun createBlock(param: BlockStorageCreate) : BareMetalx2? {
        if(param.region.isEmpty())
            throw Exception("BlockStorageCreate::region must not be empty")

        if((param.size_gb < 10 || param.size_gb > 10000))
            throw Exception("BlockStorageCreate::size_gb must be 10 <= size <= 10000")

        val url = "/blocks"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<BareMetalx2>()
        ) as BareMetalx2
    }

    fun getBlock(block_id: String) : BlockStorageBlockx? {
        val url = "/blocks/$block_id"
        return requester.get(url, RequestHelper.parser<BlockStorageBlockx>()) as BlockStorageBlockx
    }

    fun deleteBlock(block_id: String) : Any? {
        val url = "/blocks/$block_id"
        return requester.delete(url, null)
    }

    fun updateBlock(block_id: String, param: BlockStorageUpdate?) : Any? {
        val url = "/blocks/$block_id"
        if(param != null) {
            return requester.patch(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.patch(url, "", null)
    }

    fun attachBlock(block_id: String, param: BlockStorageAttach) : Any? {
        if(param.instance_id.isEmpty())
            throw Exception("BlockStorageAttach::instance_id must not be empty")

        val url = "/blocks/$block_id/attach"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun detachBlock(block_id: String, param: BlockStorageDetach?) : Any? {
        val url = "/blocks/$block_id/detach"
        if(param != null) {
            return requester.post(
                url,
                RequestHelper.jsonize(param),
                null
            )
        }
        return requester.post(url, "", null)
    }
}

data class BlockStorageBlocks (
    val blocks: List<BlockStorageBlockDetail>,
    val meta: Meta
)

data class BlockStorageBlockx (
    val block: BlockStorageBlockDetail
)

data class BlockStorageBlockDetail (
    val id: String,
    val date_created: String,
    val cost: Int,
    val status: String,
    val size_gb: Int,
    val region: String,
    val attached_to_instance: Int,
    val label: String
)

data class BlockStorageCreate (
    val region: String,
    val size_gb: Int,
    val label: String?
)

data class BlockStorageUpdate (
    val label: String?,
    val size_gb: Int?
)

data class BlockStorageAttach (
    val instance_id: String,
    val live: Boolean?
)

data class BlockStorageDetach (
    val live: Boolean?
)