package ru.meproject.pterocli.commands.files

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.mattmalec.pterodactyl4j.client.entities.PteroClient

class FilesParentCommand: CliktCommand(name = "files") {
    private val api by requireObject<PteroClient>()

    override fun run() {

    }
}