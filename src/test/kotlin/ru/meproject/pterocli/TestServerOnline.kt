package ru.meproject.pterocli

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import ru.meproject.pterocli.commands.SetupCredentials
import ru.meproject.pterocli.commands.client.PowerActionCommand
import ru.meproject.pterocli.commands.client.SendCommand
import ru.meproject.pterocli.commands.client.files.DownloadFileCommand
import ru.meproject.pterocli.commands.client.files.FilesParentCommand
import ru.meproject.pterocli.commands.client.files.RemoveFileCommand
import ru.meproject.pterocli.commands.client.files.UploadFileCommand
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestServerOnline {

    @Test
    @Order(1)
    fun test_SetupCredentials_correct() {
        val panelUrl = System.getenv("PANEL_URL")
        val adminKey = System.getenv("ADMIN_KEY")
        val clientKey = System.getenv("CLIENT_KEY")
        val command = SetupCredentials()
        val result = command.test("--panel-url $panelUrl --admin-api-key $adminKey --client-api-key $clientKey")
        assertEquals(0, result.statusCode)
        assertEquals("Credentials for https://panel.meproject.ru successfully set\n", result.stdout)
    }

    @Test
    @Order(2)
    fun testPowerAction_start_testpterocli() {
        val command = Pterocli().subcommands(ClientParentCommand().subcommands(PowerActionCommand()))
        val result = command.test("client power --servers b5030230 start")
        assertEquals(0, result.statusCode)
        assertEquals("Sending power action START to server b5030230\n", result.stdout)
    }

    @Test
    @Order(3)
    fun testUploadFile_testpterocli() {
        val file = File("test.txt")
        file.writeText("""Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt
            | ut labore et dolore magna aliqua. Elementum sagittis vitae et leo duis ut. Sit amet volutpat
            |  consequat mauris nunc.""".trimMargin())

        val command = Pterocli().subcommands(ClientParentCommand().subcommands(FilesParentCommand().subcommands(UploadFileCommand())))
        val result = command.test("client files upload --servers b5030230 plugins/ test.txt")
        assertEquals(0, result.statusCode)
        assertEquals("Uploaded file test.txt to server b5030230\n", result.stdout)
    }

    @Test
    @Order(4)
    fun testDownloadFile_testpterocli() {
        val file = File("config.yml")
        if (file.exists()) {
            file.delete()
        }
        val command = Pterocli().subcommands(ClientParentCommand().subcommands(FilesParentCommand().subcommands(DownloadFileCommand())))
        val result = command.test("client files download -s b5030230 plugins/bStats/config.yml")
        assertEquals(0, result.statusCode)
        assertEquals("Downloaded plugins/bStats/config.yml from b5030230\n", result.stdout)
    }

    @Test
    @Order(5)
    fun testDownloadFile_withoutput_testpterocli() {
        val file = File("out.yml")
        if (file.exists()) {
            file.delete()
        }
        val command = Pterocli().subcommands(ClientParentCommand().subcommands(FilesParentCommand().subcommands(DownloadFileCommand())))
        val result = command.test("client files download -s b5030230 -o out.yml plugins/bStats/config.yml")
        assertEquals(0, result.statusCode)
        assertEquals("Downloaded plugins/bStats/config.yml from b5030230 to file out.yml\n", result.stdout)
    }

    @Test
    @Order(6)
    fun testRemoveFile_testpterocli() {
        val command = Pterocli().subcommands(ClientParentCommand().subcommands(FilesParentCommand().subcommands(RemoveFileCommand())))
        val result = command.test("client files rm -s b5030230 plugins/test.txt")
        assertEquals(0, result.statusCode)
        assertEquals("Deleting file/directory plugins/test.txt on server b5030230\n", result.stdout)
    }

    @Test
    @Order(7)
    fun testSendCommand_help_testpterocli() {
        val command = Pterocli().subcommands(ClientParentCommand().subcommands(SendCommand()))
        val result = command.test("client sendcommand --servers b5030230 help")
        assertEquals(0, result.statusCode)
        assertEquals("Sending console command \"help\" to server b5030230\n", result.stdout)
    }


}