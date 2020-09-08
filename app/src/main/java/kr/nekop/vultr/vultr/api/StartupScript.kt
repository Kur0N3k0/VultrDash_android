package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class StartupScript (
    private val requester: APIRequest
) {
    fun listScripts(per_page: Int, cursor: String) : StartupScripts? {
        var url = "/startup-scripts"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<StartupScripts>()) as StartupScripts
    }

    fun getScript(startup_id: String) : StartupScriptx? {
        val url = "/startup-scripts/$startup_id"
        return requester.get(url, RequestHelper.parser<StartupScriptx>()) as StartupScriptx
    }

    fun updateScript(startup_id: String, param: StartupScriptUpdate) : Any? {
        val url = "/startup-scripts/$startup_id"
        return requester.patch(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun deleteKey(startup_id: String) : Any? {
        val url = "/startup-scripts/$startup_id"
        return requester.delete(url, null)
    }

    fun createKey(startup_id: String, param: StartupScriptCreate) : StartupScriptx? {
        val url = "/startup-scripts/$startup_id"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<StartupScriptx>()
        ) as StartupScriptx
    }
}

data class StartupScripts (
    val ssh_keys: List<SSHKeyDetail>,
    val meta: Meta
)

data class StartupScriptx (
    val ssh_key: StartupScriptDetail
)

data class StartupScriptDetail (
    val id: String,
    val date_created: String,
    val date_modified: String,
    val name: String,
    val type: String,
    val script: String
)

data class StartupScriptUpdate (
    val name: String,
    val script: String
)

data class StartupScriptCreate (
    val name: String,
    val type: String,
    val script: String
)