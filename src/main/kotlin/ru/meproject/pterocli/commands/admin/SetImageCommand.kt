package ru.meproject.pterocli.commands.admin

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.YesNoPrompt
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication
import ru.meproject.pterocli.options.AdminServerIds
import ru.meproject.pterocli.options.ConfirmationSilencer

class SetImageCommand: CliktCommand(
    name = "setimage",
    help = "update docker image on a server"
) {
    private val api by requireObject<PteroApplication>()
    private val servers by AdminServerIds()
    private val containerUrl by argument().help("Container to be used in docker format (e.g. java:jdk17)")
    private val confirm by ConfirmationSilencer()

    override fun run() {
        for (server in servers.ids) {
            if (confirm.shouldBeSilent || askImageUpdateConfirmation(server, terminal) == true) {
                api.retrieveServerById(server)
                    .flatMap { it.startupManager.setImage(containerUrl) }
                    .execute()
                echo("Successfully set \"$containerUrl\" to a server $server")
            }
        }
    }

    private fun askImageUpdateConfirmation(pServer: Long, terminal: Terminal): Boolean? {
        return YesNoPrompt(updateContainerConfirmation(pServer), terminal).ask()
    }

    private fun updateContainerConfirmation(pServer: Long) = "You sure you want to change container on server $pServer?"
}