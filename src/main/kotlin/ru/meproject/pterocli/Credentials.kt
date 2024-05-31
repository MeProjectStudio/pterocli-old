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
        File(CREDENTIALS_FILE_PATH).writeText(json)
    }

    fun loadCredentials(): Credentials {
        if (!File(CREDENTIALS_FILE_PATH).exists()) {
            throw PrintMessage("No credentials file found", statusCode = 0)
        }

        val json = File(CREDENTIALS_FILE_PATH).readText()
        return Json.decodeFromString<Credentials>(json)
    }
}

@Serializable
data class Credentials(
    val panelURL: String,
    var clientApiKey: String?,
    var applicationApiKey: String?
)
