package ru.meproject.pterocli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.PteroBuilder
import ru.meproject.pterocli.ExplicitCredentials
import ru.meproject.pterocli.incorrectCredentials

class SetupCredentials: CliktCommand(
    name = "setup",
    help = """
        setup
    """.trimIndent()) {
    private val credentials by ExplicitCredentials()

    override fun run() {
        val api = PteroBuilder.createClient(credentials.panelUrl, credentials.apiKey)
        val account = api.retrieveAccount()
    }
}