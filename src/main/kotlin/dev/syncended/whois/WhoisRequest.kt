package dev.syncended.whois

import dev.syncended.whois.tld.TldUtils
import dev.syncended.whois.tld.TopLevelDomain
import org.apache.commons.net.whois.WhoisClient

data class WhoisRequest(
    private val domain: String? = null,
    private val timeout: Int = 60_000
) {
    fun withDomain(domain: String): WhoisRequest = copy(domain = domain)
    fun withTimeout(timeout: Int): WhoisRequest = copy(timeout = timeout)

    fun execute(): WhoisResponse {
        domain ?: return WhoisResponse.Broken(BrokenDomainException(domain))
        val tld = TldUtils.decodeFromDomain(domain) ?: return WhoisResponse.Broken(BrokenDomainException(domain))

        makeRequest(domain, tld).fold(
            onSuccess = { return WhoisResponse.Success(it) },
            onFailure = { return WhoisResponse.Failed(it) }
        )
    }

    private fun makeRequest(domain: String, tld: TopLevelDomain): Result<String> {
        val whoisClient = WhoisClient()
        return runCatching {
            val whoisServer = TldUtils.getWhoisServer(tld)

            whoisClient.defaultTimeout = timeout
            whoisClient.connectTimeout = timeout
            whoisClient.connect(whoisServer)
            whoisClient.query(domain)
        }.also { runCatching { whoisClient.disconnect() } }
    }
}