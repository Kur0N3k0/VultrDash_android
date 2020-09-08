package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest

class SSHKey (
    private val requester: APIRequest
) {
    fun listKey(per_page: Int, cursor: String) : SSHKeys? {

    }

    fun
}

data class SSHKeys (
    val ssh_keys: List<SSHKeyDetail>,
    val meta: Meta
)

data class SSHKeyDetail (
    val
)