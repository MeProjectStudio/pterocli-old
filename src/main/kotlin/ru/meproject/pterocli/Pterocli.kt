package ru.meproject.pterocli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.mattmalec.pterodactyl4j.PteroBuilder
import ru.meproject.pterocli.commands.SetupCredentials
import ru.meproject.pterocli.commands.VersionCommand
import ru.meproject.pterocli.commands.admin.GetImageCommand
import ru.meproject.pterocli.commands.admin.GetStartupCommand
import ru.meproject.pterocli.commands.admin.SetImageCommand
import ru.meproject.pterocli.commands.admin.SetStartupCommand
import ru.meproject.pterocli.commands.client.PowerActionCommand
import ru.meproject.pterocli.commands.client.SendCommand
import ru.meproject.pterocli.commands.client.backups.CreateBackupCommand
import ru.meproject.pterocli.commands.client.files.DownloadFileCommand
import ru.meproject.pterocli.commands.client.files.FilesParentCommand
import ru.meproject.pterocli.commands.client.files.RemoveFileCommand
import ru.meproject.pterocli.commands.client.files.UploadFileCommand
import ru.meproject.pterocli.options.AdminExplicitCredentials
import ru.meproject.pterocli.options.ClientExplicitCredentials

fun main(args: Array<String>) = Pterocli()
    .subcommands(
        VersionCommand(),
        SetupCredentials(),
        ClientParentCommand().subcommands(
            PowerActionCommand(),
            SendCommand(),
            CreateBackupCommand(),
            FilesParentCommand().subcommands(
                DownloadFileCommand(),
                RemoveFileCommand(),
                UploadFileCommand()
            )
        ),
        ApplicationParentCommand().subcommands(
            GetStartupCommand(),
            SetStartupCommand(),
            GetImageCommand(),
            SetImageCommand()
        )
    )
    .main(args)

class Pterocli : CliktCommand(allowMultipleSubcommands = true) {
    override fun run() {
        // nothing
    }
}

class ClientParentCommand: CliktCommand(
    name = "client",
    help = "Commands that use Client API keys. Underlying user need to be a user with sufficient permissions on the server"
) {
    private val credentials by ClientExplicitCredentials()
    override fun run() {
        currentContext.obj = PteroBuilder.createClient(credentials.panelUrl, credentials.apiKey)
    }
}

class ApplicationParentCommand : CliktCommand(
    name = "admin",
    help = "Commands that use Application API keys. Keys need to be created in Admin Area of Pterodactyl instance"
) {
    private val credentials by AdminExplicitCredentials()
    override fun run() {
        currentContext.obj = PteroBuilder.createApplication(credentials.panelUrl, credentials.apiKey)
    }

}