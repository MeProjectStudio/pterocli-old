package ru.meproject.pterocli.commands.client

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.types.enum
import com.mattmalec.pterodactyl4j.PowerAction
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.options.ServerIds

class PowerActionCommand : CliktCommand(
    name = "power",
    help = "send Power Action to a server"
) {
    private val api by requireObject<PteroClient>()

    private val servers by ServerIds()
    private val action: PowerAction by argument().help("Power Action to send to a server").enum()

    override fun run() {
        for (server in servers.ids) {
            api.retrieveServerByIdentifier(server)
                .flatMap { it.setPower(action) }
                .execute()
            echo("Sending power action $action to server $server")
        }
    }
}