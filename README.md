# WHOIS

Whois implementation in Kotlin (Java).

Inspired by https://github.com/rfc1036/whois repository

## Sample

Sample integration with library on kotlin

Build request

```kotlin
val request = WhoisRequest(domain = "syncended.dev")
// or
val request = WhoisRequest().withDomain("syncended.dev")
```

Request execution

```kotlin
// Get typed response, without any exception thrown
val response = request.execute()
// Or just get success (with exception throwing)
val response = request.executeUnsafe()
// Do any stuff with that information
```
