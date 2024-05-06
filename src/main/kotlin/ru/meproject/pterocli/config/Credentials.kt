package ru.meproject.pterocli.config

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(val client: CredentialsBundle, val application: CredentialsBundle)

@Serializable
data class CredentialsBundle(val panelURL: String, val apiKey: String)
