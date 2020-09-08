package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest
import kr.nekop.vultr.vultr.api.util.RequestHelper

class User (
    private val requester: APIRequest
) {
    fun listScripts(per_page: Int, cursor: String) : Users? {
        var url = "/users"
        if(per_page != 25 || !cursor.equals(""))
            url += "?per_page=$per_page&cursor=$cursor"

        return requester.get(url, RequestHelper.parser<Users>()) as Users
    }

    fun getUser(user_id: String) : Userx? {
        val url = "/users/$user_id"
        return requester.get(url, RequestHelper.parser<Userx>()) as Userx
    }

    fun deleteUser(user_id: String) : Any? {
        val url = "/users/$user_id"
        return requester.delete(url, null)
    }

    fun updateUser(user_id: String, param: UserUpdate) : Any? {
        val url = "/users/$user_id"
        return requester.patch(
            url,
            RequestHelper.jsonize(param),
            null
        )
    }

    fun createUser(startup_id: String, param: UserCreate) : Userx? {
        val url = "/users/$startup_id"
        return requester.post(
            url,
            RequestHelper.jsonize(param),
            RequestHelper.parser<Userx>()
        ) as Userx
    }
}

data class Users (
    val users: List<UserDetail>,
    val meta: Meta
)

data class Userx (
    val user: UserDetail
)

data class UserDetail (
    val id: String,
    val email: String,
    val api_enabled: Boolean,
    val acls: List<String>
)

data class UserUpdate (
    val email: String,
    val name: String,
    val password: String,
    val api_enabled: Boolean,
    val acls: List<String>
)

data class UserCreate (
    val email: String,
    val name: String,
    val password: String,
    val api_enabled: Boolean,
    val acls: List<String>
)