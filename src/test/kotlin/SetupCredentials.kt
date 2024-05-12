import com.github.ajalt.clikt.testing.test
import ru.meproject.pterocli.SetupCredentialsClientKeyFailure
import ru.meproject.pterocli.commands.SetupCredentials
import kotlin.test.Test
import kotlin.test.assertEquals

class SetupCredentials {
    @Test
    fun test_SetupCredentials_panelUrl_bogus() {
        val command = SetupCredentials()
        val result = command.test("--panel-url http://notsorealwebsite.example")
        assertEquals(1, result.statusCode)
    }

    @Test
    fun test_SetupCredentials_all_bogus() {
        val command = SetupCredentials()
        val result = command.test("--panel-url http://notsorealwebsite.example --admin-api-key verynotrealkey --client-api-key thisoneisrealtrustme")
        assertEquals(SetupCredentialsClientKeyFailure, result.stderr)

    }
}