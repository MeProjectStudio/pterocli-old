package ru.meproject.pterocli

import com.github.ajalt.clikt.testing.test
import ru.meproject.pterocli.commands.SetupCredentials
import kotlin.test.Test
import kotlin.test.assertEquals

class SetupCredentials {
    // $Env:<variable-name> = "<new-value>"


    @Test
    fun test_SetupCredentials_panelUrl_bogus() {
        val panelUrl = System.getenv("PANEL_URL")
        val adminKey = System.getenv("ADMIN_KEY")
        val clientKey = System.getenv("CLIENT_KEY")
        val command = SetupCredentials()
        val result = command.test("--panel-url $panelUrl")
        assertEquals(1, result.statusCode)
        assertEquals("At least one api key either Client or Application needs to be specified\n", result.stdout)
    }

    @Test
    fun test_SetupCredentials_bogus_keys() {
        val panelUrl = System.getenv("PANEL_URL")
        val adminKey = "gibberish"
        val clientKey = "moregibberish"
        val command = SetupCredentials()
        val result = command.test("--panel-url $panelUrl --admin-api-key $adminKey --client-api-key $clientKey")
        assertEquals(1, result.statusCode)
        assertEquals("There seems to be an issue with Client key specified\n", result.stdout)
    }

    @Test
    fun test_SetupCredentials_correct() {
        val panelUrl = System.getenv("PANEL_URL")
        val adminKey = System.getenv("ADMIN_KEY")
        val clientKey = System.getenv("CLIENT_KEY")
        val command = SetupCredentials()
        val result = command.test("--panel-url $panelUrl --admin-api-key $adminKey --client-api-key $clientKey")
        assertEquals(0, result.statusCode)
        assertEquals("Credentials for https://panel.meproject.ru successfully set\n", result.stdout)
    }


}