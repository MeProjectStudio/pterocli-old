package ru.meproject.pterocli.options

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required

class ExplicitCredentials: OptionGroup() {
    val panelUrl: String by option("--url", "-u")
        .required()
        .help("Pterodactyl Instance")
    val apiKey: String by option("--api-key", "-a")
        .required()
        .help("API key for an Instance")
}