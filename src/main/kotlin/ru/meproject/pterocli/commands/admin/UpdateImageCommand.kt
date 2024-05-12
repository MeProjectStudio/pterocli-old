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
import ru.meproject.pterocli.options.ConfirmationSilencer
import ru.meproject.pterocli.options.ServerIds
import ru.meproject.pterocli.updateStartupConfirmation

class UpdateImageCommand: CliktCommand(
    name = "updateimage",
    help = "Updates docker image to be used for a server"
) {
    private val api by requireObject<PteroApplication>()
    private val servers by ServerIds()
    private val containerUrl by argument().help("Container to be used in docker format (e.g. java:jdk17)")
    private val confirm by ConfirmationSilencer()

    override fun run() {
        for (server in servers.ids) {
            if (confirm.shouldBeSilent == true || askImageUpdateConfirmation(server, terminal) == true) {
                api.retrieveServerById(server)
                    .flatMap { it.startupManager.setImage(containerUrl) }
                    .execute()
            }
        }
    }

    private fun askImageUpdateConfirmation(pServer: String, terminal: Terminal): Boolean? {
        return YesNoPrompt(updateStartupConfirmation(pServer), terminal).ask()
    }
}