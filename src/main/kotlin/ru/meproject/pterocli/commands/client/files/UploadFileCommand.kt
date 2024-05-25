package ru.meproject.pterocli.commands.client.files

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.arguments.unique
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.types.file
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.options.ServerIds
import java.io.File

class UploadFileCommand: CliktCommand(
    name = "upload",
    help = "upload local file to a server"
) {
    private val api by requireObject<PteroClient>()
    private val servers by ServerIds()

    private val remotePath: String by argument().help("Remote path to directory to upload file to")
    private val localFiles: List<File> by argument()
        .file(mustExist = true, mustBeReadable = true)
        .multiple()
        .help("Local file to be uploaded")



    override fun run() {
        for (server in servers.ids) {
            for (file in localFiles) {
                api.retrieveServerByIdentifier(server)
                    .flatMap { it.retrieveDirectory(remotePath) }
                    .flatMap { it.upload().addFile(file) }
                    .execute()
                echo("Uploaded file ${file.name} to server $server")
            }
        }
    }

}