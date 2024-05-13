package ru.meproject.pterocli.commands.client.files

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.options.ServerIds
import java.io.File
import java.util.concurrent.CompletableFuture
import kotlin.jvm.optionals.getOrNull

class DownloadFileCommand: CliktCommand(
    name = "download",
    help = "Download remote file from a server (only files, no support for directories)"
) {
    private val api by requireObject<PteroClient>()
    private val servers by ServerIds()

    private val remotePath: String by argument().help("Remote path to a file to be downloaded")

    override fun run() {
        val extracted = extractParentDirectory(remotePath)
        for (server in servers.ids) {
            if (checkIfRemoteIsFile(remotePath, server)) {
                val dir = api.retrieveServerByIdentifier(server)
                    .flatMap { it.retrieveDirectory(extracted.first) }
                    .map { it.getFileByName(extracted.second) }
                    .execute()

                dir.ifPresent {
                    it.retrieveDownload().map { f ->
                        f.downloadToFile()
                    }
                    echo("Downloaded $remotePath from $server")
                }
            }

        }
    }

    private fun extractParentDirectory(remoteFilePath: String): Pair<String, String> {
        val components = remoteFilePath.split('/')
        return if (components.size >= 2) {
            val parentDir = components.dropLast(1).joinToString("/")
            val file = components.last()
            return parentDir to file
        } else {
            remoteFilePath to ""
        }
    }

    private fun checkIfRemoteIsFile(remoteFilePath: String, pServer: String): Boolean {
        return api.retrieveServerByIdentifier(pServer)
            .flatMap { it.retrieveDirectory(remoteFilePath) }
            .map { it.isFile }
            .execute()
    }
}