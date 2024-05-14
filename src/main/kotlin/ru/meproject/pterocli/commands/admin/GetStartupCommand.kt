package ru.meproject.pterocli.commands.admin

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication
import ru.meproject.pterocli.options.AdminServerIds

class GetStartupCommand: CliktCommand(
    name = "getstartup",
    help = "Retrieves current startup command by a server"
) {
    private val api by requireObject<PteroApplication>()
    private val servers by AdminServerIds()

    override fun run() {
        for (server in servers.ids) {
            echo(api.retrieveServerById(server).map { it.container.startupCommand }.execute(), err = false)
        }
    }
}