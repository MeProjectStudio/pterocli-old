package ru.meproject.pterocli.commands.files

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.ServerIds

class RemoveFileCommand: CliktCommand(name = "rm") {
    private val api by requireObject<PteroClient>()
    private val servers by ServerIds()
    private val paths: List<String> by argument().multiple()

    override fun run() {
        for (server in servers.ids) {
            for (remotePath in paths) {
                api.retrieveServerByIdentifier(server).flatMap { it.retrieveDirectory() }
                    .flatMap {
                        echo("Deleting file/directory ${it.name} on server $server")
                        it.deleteFiles()
                    }
                    .execute()
            }
        }
    }
}