package ru.meproject.pterocli.options

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option

class ConfirmationSilencer : OptionGroup() {
    val shouldBeSilent by option("-y")
        .flag(default = false)
        .help("Skips confirmation for destructive admin actions")
}