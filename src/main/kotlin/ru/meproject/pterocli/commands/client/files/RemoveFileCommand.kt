package ru.meproject.pterocli.commands.client.files

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.commands.client.files.FilesParentCommand.Companion.extractParentDirectory
import ru.meproject.pterocli.options.ServerIds

class RemoveFileCommand: CliktCommand(
    name = "rm",
    help = "Remove remote file/directory on a server"
) {
    private val api by requireObject<PteroClient>()
    private val servers by ServerIds()
    private val paths: List<String> by argument().multiple().help("Paths to files/directories to be removed")

    override fun run() {
        for (server in servers.ids) {
            for (remotePath in paths) {
                val extracted = extractParentDirectory(remotePath)
                val file = api.retrieveServerByIdentifier(server)
                    .flatMap { it.retrieveDirectory(extracted.first) }
                    .map { it.getFileByName(extracted.second) }
                    .execute()
                api.retrieveServerByIdentifier(server)
                    .flatMap { it.retrieveDirectory(extracted.first) }
                    .flatMap { it.deleteFiles().addFile(file.get()) }
                    .execute()
                echo("Deleting file/directory $remotePath on server $server")
            }
        }
    }
}