package dev.syncended.whois

sealed class WhoisResponse {

    data class Broken(
        val reason: Exception
    ) : WhoisResponse()

    data class Failed(
        val reason: Throwable
    ) : WhoisResponse()

    data class Success(
        val data: String
    ) : WhoisResponse()
}