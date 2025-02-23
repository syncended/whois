package dev.syncended.whois

sealed class WhoisException(message: String): Exception(message)

class BrokenDomainException(domain: String?): WhoisException("Domain \"$domain\" is invalid to check whois")