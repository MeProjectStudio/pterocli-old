package ru.meproject.pterocli.options

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import ru.meproject.pterocli.CredentialsStore

class AdminExplicitCredentials: OptionGroup() {
    val panelUrl: String by option("--url", "-u")
        .defaultLazy { CredentialsStore.loadCredentials()?.panelURL!! }
        .help("Pterodactyl Instance URL")
    val apiKey: String by option("--api-key", "-a")
        .defaultLazy { CredentialsStore.loadCredentials()?.applicationApiKey!! }
        .help("Admin API key for an Instance")
}