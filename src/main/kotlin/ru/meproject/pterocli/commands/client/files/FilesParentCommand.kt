package ru.meproject.pterocli.commands.client.files

import com.github.ajalt.clikt.core.CliktCommand

class FilesParentCommand: CliktCommand(
    name = "files",
    help = "Commands that perform actions on filesystem of a server"
) {

    override fun run() {
        // nothing
    }
}