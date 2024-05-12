package ru.meproject.pterocli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.PteroBuilder
import ru.meproject.pterocli.commands.SetupCredentials
import ru.meproject.pterocli.commands.admin.GetImageCommand
import ru.meproject.pterocli.commands.admin.GetStartupCommand
import ru.meproject.pterocli.commands.admin.UpdateImageCommand
import ru.meproject.pterocli.commands.admin.UpdateStartupCommand
import ru.meproject.pterocli.commands.client.PowerActionCommand
import ru.meproject.pterocli.commands.client.SendCommand
import ru.meproject.pterocli.commands.client.files.DownloadFileCommand
import ru.meproject.pterocli.commands.client.files.FilesParentCommand
import ru.meproject.pterocli.commands.client.files.RemoveFileCommand
import ru.meproject.pterocli.commands.client.files.UploadFileCommand
import ru.meproject.pterocli.options.ExplicitCredentials

fun main(args: Array<String>) = Pterocli()
    .subcommands(
        SetupCredentials(),
        ClientParentCommand()
            .subcommands(
                PowerActionCommand(),
                SendCommand(),
                FilesParentCommand()
                    .subcommands(
                        DownloadFileCommand(),
                        RemoveFileCommand(),
                        UploadFileCommand()
                    )
            ),
        ApplicationParentCommand()
            .subcommands(
                GetStartupCommand(),
                UpdateStartupCommand(),
                GetImageCommand(),
                UpdateImageCommand()
            )
    )
    .main(args)

class Pterocli : CliktCommand(allowMultipleSubcommands = true) {
    override fun run() {
        // nothing
    }
}

class ClientParentCommand: CliktCommand(name = "client", allowMultipleSubcommands = true) {
    private val credentials by ExplicitCredentials()
    override fun run() {
        currentContext.obj = PteroBuilder.createClient(credentials.panelUrl, credentials.apiKey)
    }
}

class ApplicationParentCommand : CliktCommand(name = "admin", allowMultipleSubcommands = true) {
    private val credentials by ExplicitCredentials()
    override fun run() {
        currentContext.obj = PteroBuilder.createApplication(credentials.panelUrl, credentials.apiKey)
    }

}