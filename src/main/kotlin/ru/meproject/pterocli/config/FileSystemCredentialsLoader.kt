package ru.meproject.pterocli.config

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object FileSystemCredentialsLoader {
    private const val CREDENTIALS_FILE_PATH = "credentials.json"

    fun saveCredentials(credentials: Credentials) {
        val json = Json.encodeToString(credentials)
        File(CREDENTIALS_FILE_PATH).writeText(json)
    }

    fun loadCredentials(): Credentials? {
        if (!File(CREDENTIALS_FILE_PATH).exists()) {
            return null
        }

        val json = File(CREDENTIALS_FILE_PATH).readText()
        return Json.decodeFromString<Credentials>(json)
    }
}