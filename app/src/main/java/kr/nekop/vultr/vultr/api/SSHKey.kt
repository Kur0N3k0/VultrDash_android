package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class SSHKey (
    private val requester: APIRequest
) {
    fun listKey(per_page: Int, cursor: String) : SSHKeys? {
        var url = "/ssh-keys"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<SSHKeys>()) as SSHKeys
    }

    fun getKey(ssh_key_id: String) : SSHKeyx? {
        val url = "/ssh-keys/$ssh_key_id"
        return requester.get(url, RequestHelper.parser<SSHKeyx>()) as SSHKeyx
    }

    fun updateKey(ssh_key_id: String, param: SSHKeyUpdate) : Any? {
        val url = "/ssh-keys/$ssh_key_id"
        return requester.patch(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun deleteKey(ssh_key_id: String) : Any? {
        val url = "/ssh-keys/$ssh_key_id"
        return requester.delete(url, null)
    }

    fun createKey(ssh_key_id: String, param: SSHKeyCreate) : SSHKeyx? {
        val url = "/ssh-keys/$ssh_key_id"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<SSHKeyx>()
        ) as SSHKeyx
    }
}

data class SSHKeys (
    val ssh_keys: List<SSHKeyDetail>,
    val meta: Meta
)

data class SSHKeyx (
    val ssh_key: SSHKeyDetail
)

data class SSHKeyDetail (
    val id: String,
    val date_created: String,
    val name: String,
    val ssh_key: String
)

data class SSHKeyUpdate (
    val name: String,
    val ssh_key: String
)

data class SSHKeyCreate (
    val name: String,
    val ssh_key: String
)