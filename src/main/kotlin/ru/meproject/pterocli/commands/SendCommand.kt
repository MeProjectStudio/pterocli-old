package ru.meproject.pterocli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.PteroBuilder
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.ExplicitCredentials
import ru.meproject.pterocli.ServerIds

class SendCommand: CliktCommand(
    name= "sendcommand",
    help = """
        Sends console command to specified servers without any consideration for command's success or output
    """.trimIndent()) {
    private val api by requireObject<PteroClient>()
    private val servers by ServerIds()
    private val serverCommand: String by argument()

    override fun run() {
        for (server in servers.ids) {
            api.retrieveServerByIdentifier(server)
                .flatMap {
                    echo("Sending console command \"$serverCommand\" to server $server")
                    it.sendCommand(serverCommand)
                }
                .execute()
        }
    }
}