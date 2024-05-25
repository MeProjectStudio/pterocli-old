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

class SetStartupCommand: CliktCommand(
    name = "setstartup",
    help = "update startup command on a server"
) {
    private val api by requireObject<PteroApplication>()
    private val servers by AdminServerIds()
    private val startupCommand by argument().help("New startup command to be set on a server")
    private val confirm by ConfirmationSilencer()

    override fun run() {
        for (server in servers.ids) {
            if (confirm.shouldBeSilent || askStartupCommandConfirmation(server, terminal) == true) {
                api.retrieveServerById(server)
                    .flatMap { it.startupManager.setStartupCommand(startupCommand) }
                    .execute()
                echo("Successfully set \"$startupCommand\" to a server $server")
            }
        }
    }

    private fun askStartupCommandConfirmation(pServer: Long, terminal: Terminal): Boolean? {
        return YesNoPrompt(updateStartupConfirmation(pServer), terminal).ask()
    }

    private fun updateStartupConfirmation(pServer: Long) = "You sure you want to change startup command on server $pServer?"
}