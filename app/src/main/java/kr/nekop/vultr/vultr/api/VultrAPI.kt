package kr.nekop.vultr.vultr.api

import kr.nekop.vultr.vultr.api.util.APIRequest

class VultrAPI(
    private val apiKey: String,
    requester: APIRequest = APIRequest(apiKey)
) {
    val account: Account = Account(requester)
    val application: Application = Application(requester)
    val backups: Backups = Backups(requester)
    val baremetal: BareMetal = BareMetal(requester)
    val blockstorage: BlockStorage = BlockStorage(requester)
    val dns: DNS = DNS(requester)
    val firewall: Firewall = Firewall(requester)
    val iso: ISO = ISO(requester)
    val loadbalancer: LoadBalancer = LoadBalancer(requester)
}