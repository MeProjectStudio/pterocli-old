package ru.meproject.pterocli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.PteroBuilder
import ru.meproject.pterocli.commands.PowerActionCommand
import ru.meproject.pterocli.commands.SendCommand
import ru.meproject.pterocli.commands.files.FilesParentCommand
import ru.meproject.pterocli.commands.files.RemoveFileCommand
import ru.meproject.pterocli.commands.files.UploadFileCommand

fun main(args: Array<String>) = Pterocli()
    .subcommands(PowerActionCommand(), SendCommand(),
        FilesParentCommand()
            .subcommands(RemoveFileCommand(), UploadFileCommand())
    )
    .main(args)

class Pterocli : CliktCommand() {
    private val credentials by ExplicitCredentials()

    override fun run() {
        currentContext.obj = PteroBuilder.create(credentials.panelUrl, credentials.apiKey)
    }

}