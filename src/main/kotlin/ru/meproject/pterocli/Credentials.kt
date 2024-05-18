package ru.meproject.pterocli

import com.github.ajalt.clikt.core.PrintMessage
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.*

object CredentialsStore {
    private const val CREDENTIALS_FILE_PATH = "credentials.json"

    fun saveCredentials(credentials: Credentials) {
        val json = Json.encodeToString(credentials)
        val base64 = Base64.getEncoder().encodeToString(json.toByteArray())
        File(CREDENTIALS_FILE_PATH).writeText(base64)
    }

    fun loadCredentials(): Credentials {
        if (!File(CREDENTIALS_FILE_PATH).exists()) {
            throw PrintMessage("No credentials file found", statusCode = 0)
        }

        val base64 = File(CREDENTIALS_FILE_PATH).readText()
        val json = String(Base64.getDecoder().decode(base64))
        return Json.decodeFromString<Credentials>(json)
    }
}

@Serializable
data class Credentials(
    val panelURL: String,
    var clientApiKey: String?,
    var applicationApiKey: String?
)
