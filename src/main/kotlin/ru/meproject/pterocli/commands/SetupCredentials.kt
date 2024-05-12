package ru.meproject.pterocli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.mattmalec.pterodactyl4j.PteroBuilder
import ru.meproject.pterocli.Credentials
import ru.meproject.pterocli.CredentialsStore

class SetupCredentials: CliktCommand(
    name = "setupcredentials",
    help = "This command sets up credentials file that would be used by default"
) {

    private val panelUrl: String by option("--panel-url").required()
        .help("Pterodactyl instance URL")

    private val clientApiKey: String? by option("--client-api-key")
        .help("Client (user) API key for Pterodactyl instance")

    private val adminApiKey: String? by option("--admin-api-key")
        .help("Application (admin) API key for Pterodactyl instance")

    override fun run() {
        if (clientApiKey == null && adminApiKey == null) {
            throw PrintMessage("At least one api key either Client or Application needs to be specified")
        }
        val existingCredentials = CredentialsStore.loadCredentials()
        val updatedCredentials = existingCredentials ?: Credentials(panelUrl, null, null)
        // Set client API key if provided
        clientApiKey?.let {
            testClientKey(panelUrl, it)
            updatedCredentials.clientApiKey = it
        }
        // Set admin API key if provided
        adminApiKey?.let {
            testApplicationKey(panelUrl, it)
            updatedCredentials.applicationApiKey = it
        }
        CredentialsStore.saveCredentials(updatedCredentials)
        echo("Credentials for $panelUrl successfully set")
    }

    private fun testClientKey(pPanelUrl: String, pClientKey: String) {
        val api = PteroBuilder.createClient(pPanelUrl, pClientKey)
        try {
            api.retrieveAccount().execute()
        } catch (e: Exception) {
            throw PrintMessage("There seems to be an issue with Client key specified\n${e.message}")
        }
    }

    private fun testApplicationKey(pPanelUrl: String, pApplicationKey: String) {
        val api = PteroBuilder.createApplication(pPanelUrl, pApplicationKey)
        try {
            api.retrieveServers().execute()
        } catch (e: Exception) {
            throw PrintMessage("There seems to be an issue with Application key specified\n${e.message}")
        }
    }
}

