package dev.syncended.whois

import dev.syncended.whois.tld.TldUtils
import dev.syncended.whois.tld.TopLevelDomain
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

object WhoisIntegrationTest {

    @ParameterizedTest(name = "Try to request syncended.{0}")
    @MethodSource("tldList")
    fun `ensure all tld request is success`(tld: TopLevelDomain) {
        val domain = "syncended.${tld.tldName}"
        val request = WhoisRequest(domain = domain, timeout = 1_000)
        val response = request.execute()
        (response is WhoisResponse.Success).shouldBeTrue()
    }

    @JvmStatic
    fun tldList(): List<TopLevelDomain> = TopLevelDomain.entries
        // Skip unsupported domains
        // TODO: Remove it soon
        .filter { runCatching { TldUtils.getWhoisServer(it) }.isSuccess }
}