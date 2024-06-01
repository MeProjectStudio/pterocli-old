package ru.meproject.pterocli.commands.client.backups

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import ru.meproject.pterocli.options.ServerIds
import java.time.LocalDateTime

class CreateBackupCommand : CliktCommand(
    name = "createbackup",
    help = "creates backup on a server"
) {
    private val api by requireObject<PteroClient>()

    private val servers by ServerIds()
    private val backupName by option()
        .default("Backup with pterocli {ts}")
        .help("Optional backup name. Supports templating. Add {ts} for a timestamp or {srv} for server Id")

    override fun run() {
        for (server in servers.ids) {
            val formattedName = backupName
                .replace("{ts}", LocalDateTime.now().toString())
                .replace("{srv}", server)
            api.retrieveServerByIdentifier(server)
                .flatMap { it.backupManager.createBackup().setName(formattedName) }
                .execute()
        }
    }

}