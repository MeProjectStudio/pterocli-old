package ru.meproject.pterocli.commands

import com.github.ajalt.clikt.core.CliktCommand
import ru.meproject.pterocli.PTEROCLI_GIT_REV
import ru.meproject.pterocli.PTEROCLI_VERSION

class VersionCommand : CliktCommand(name = "version", help = "shows current pterocli version") {

    override fun run() {
        echo("""
            pterocli version: $PTEROCLI_VERSION rev: $PTEROCLI_GIT_REV
            Java Vendor: ${getCurrentJavaVendor()} Java Version: ${getCurrentJavaVer()}
        """.trimIndent())
    }

    private fun getCurrentJavaVendor(): String {
        return System.getProperty("java.vendor")
    }
    private fun getCurrentJavaVer(): String {
        return System.getProperty("java.version")
    }

}