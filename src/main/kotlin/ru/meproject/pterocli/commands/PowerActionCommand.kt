package ru.meproject.pterocli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.types.enum
import com.mattmalec.pterodactyl4j.PowerAction
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.ServerIds

class PowerActionCommand : CliktCommand(
    name = "power",
    help = """
        Sends PowerAction to specified servers. 
    """.trimIndent()) {
    private val api by requireObject<PteroClient>()

    private val servers by ServerIds()
    private val action: PowerAction by argument().enum()

    override fun run() {
        for (server in servers.ids) {
            api.retrieveServerByIdentifier(server)
                .flatMap { clientServer ->
                    echo("Sending power action $action to server $server")
                    clientServer.setPower(action)
                }
                .execute()
        }
    }
}