package ru.meproject.pterocli.commands.client.files

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.commands.client.files.FilesParentCommand.Companion.extractParentDirectory

class DownloadFileCommand: CliktCommand(
    name = "download",
    help = "Download remote file from a single server (only files, no support for directories)"
) {
    private val api by requireObject<PteroClient>()
    private val server by option("-s","--server").required().help("Can only take single server as argument")
    private val outputFile by option("-o", "--output").help("Optional output filename")

    private val remotePath: String by argument().help("Remote path to a file to be downloaded")

    override fun run() {
        val extracted = extractParentDirectory(remotePath)
        val remoteFile = api.retrieveServerByIdentifier(server)
            .flatMap { it.retrieveDirectory(extracted.first) }
            .flatMap { it.getFileByName(extracted.second).get().retrieveDownload() }
            .execute()

        if (outputFile != null) {
            remoteFile.downloadToFile(outputFile).join()
            echo("Downloaded $remotePath from $server to file $outputFile")
        } else {
            remoteFile.downloadToFile().join()
            echo("Downloaded $remotePath from $server")
        }
    }


}