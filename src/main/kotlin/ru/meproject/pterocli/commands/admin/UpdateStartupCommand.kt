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

class UpdateStartupCommand: CliktCommand(
    name = "updatestartup",
    help = "Update startup command on a server"
) {
    private val api by requireObject<PteroApplication>()
    private val servers by ServerIds()
    private val startupCommand by argument().help("New startup command to be set on a server")
    private val confirm by ConfirmationSilencer()

    override fun run() {
        for (server in servers.ids) {
            if (confirm.shouldBeSilent == true || askStartupCommandConfirmation(server, terminal) == true) {
                api.retrieveServerById(server)
                    .flatMap { it.startupManager.setStartupCommand(startupCommand) }
                    .execute()
            }
        }
    }

    private fun askStartupCommandConfirmation(pServer: String, terminal: Terminal): Boolean? {
        return YesNoPrompt(updateStartupConfirmation(pServer), terminal).ask()
    }
}