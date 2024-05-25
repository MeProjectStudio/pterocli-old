package ru.meproject.pterocli.commands.client.files

import com.github.ajalt.clikt.core.CliktCommand

class FilesParentCommand: CliktCommand(
    name = "files",
    help = "perform actions on filesystem of a server"
) {

    override fun run() {
        // nothing
    }

    companion object {
        fun extractParentDirectory(remoteFilePath: String): Pair<String, String> {
            val components = remoteFilePath.split('/')
            return if (components.size >= 2) {
                val parentDir = components.dropLast(1).joinToString("/")
                val file = components.last()
                return parentDir to file
            } else {
                remoteFilePath to ""
            }
        }
    }
}