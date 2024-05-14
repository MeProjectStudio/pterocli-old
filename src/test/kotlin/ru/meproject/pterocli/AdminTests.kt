package ru.meproject.pterocli

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import ru.meproject.pterocli.commands.SetupCredentials
import ru.meproject.pterocli.commands.admin.GetImageCommand
import ru.meproject.pterocli.commands.admin.GetStartupCommand
import ru.meproject.pterocli.commands.admin.SetStartupCommand
import ru.meproject.pterocli.commands.admin.SetImageCommand
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AdminTests {

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
    fun testGetStartup() {
        val command = Pterocli().subcommands(ApplicationParentCommand().subcommands(GetStartupCommand()))
        val result = command.test("admin getstartup -s 279")
        assertEquals(0, result.statusCode)
        assertEquals("java -Xms128M -XX:MaxRAMPercentage=95.0 -Dterminal.jline=false -Dterminal.ansi=true -jar {{SERVER_JARFILE}}\n", result.stdout)
    }

    @Test
    @Order(3)
    fun testSetStartup() {
        val command = Pterocli().subcommands(ApplicationParentCommand().subcommands(SetStartupCommand()))
        val result = command.test("admin setstartup -s 279 -y \"java -Xms128M -XX:MaxRAMPercentage=95.0 -Dterminal.jline=false -Dterminal.ansi=true -jar {{SERVER_JARFILE}}\"")
        assertEquals(0, result.statusCode)
        assertEquals("Successfully set \"java -Xms128M -XX:MaxRAMPercentage=95.0 -Dterminal.jline=false -Dterminal.ansi=true -jar {{SERVER_JARFILE}}\" to a server 279\n", result.stdout)
    }

    @Test
    @Order(4)
    fun testGetImage() {
        val command = Pterocli().subcommands(ApplicationParentCommand().subcommands(GetImageCommand()))
        val result = command.test("admin getimage -s 279")
        assertEquals(0, result.statusCode)
        assertEquals("ghcr.io/pterodactyl/yolks:java_17\n", result.stdout)
    }

    @Test
    @Order(5)
    fun testSetImage() {
        val command = Pterocli().subcommands(ApplicationParentCommand().subcommands(SetImageCommand()))
        val result = command.test("admin setimage -s 279 -y \"ghcr.io/pterodactyl/yolks:java_17\"")
        assertEquals(0, result.statusCode)
        assertEquals("Successfully set \"ghcr.io/pterodactyl/yolks:java_17\" to a server 279\n", result.stdout)
    }
}