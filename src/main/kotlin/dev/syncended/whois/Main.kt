package dev.syncended.whois

fun main() {
    val response = WhoisRequest(domain = "syncended.dev", timeout = 1000).execute()
    println(response)
}