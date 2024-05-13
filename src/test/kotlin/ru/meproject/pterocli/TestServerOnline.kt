package ru.meproject.pterocli

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import ru.meproject.pterocli.commands.client.PowerActionCommand
import ru.meproject.pterocli.commands.client.SendCommand
import ru.meproject.pterocli.commands.client.files.DownloadFileCommand
import ru.meproject.pterocli.commands.client.files.FilesParentCommand
import ru.meproject.pterocli.commands.client.files.UploadFileCommand
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class TestServerOnline {

    @Test
    fun testPowerAction_start_testpterocli() {
        val command = Pterocli().subcommands(ClientParentCommand().subcommands(PowerActionCommand()))
        val result = command.test("client power --servers b5030230 start")
        assertEquals(0, result.statusCode)
        assertEquals("Sending power action START to server b5030230\n", result.stdout)
    }

    @Test
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
    fun testDownloadFile_testpterocli() {
        val file = File("test.txt")
        if (file.exists()) {
            file.delete()
        }

        val command = Pterocli().subcommands(ClientParentCommand().subcommands(FilesParentCommand().subcommands(DownloadFileCommand())))
        val result = command.test("client files download --servers b5030230 /home/container/plugins/test.txt")
        assertEquals(0, result.statusCode)
        assertEquals("Downloaded plugins/test.txt from b5030230\n", result.stdout)
    }

    @Test
    fun testSendCommand_help_testpterocli() {
        val command = Pterocli().subcommands(ClientParentCommand().subcommands(SendCommand()))
        val result = command.test("client sendcommand --servers b5030230 help")
        assertEquals(0, result.statusCode)
        assertEquals("Sending console command \"thisisatestcommand\" to server b5030230\n", result.stdout)
    }


}