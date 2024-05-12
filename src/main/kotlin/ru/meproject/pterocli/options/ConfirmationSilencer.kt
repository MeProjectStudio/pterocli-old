package ru.meproject.pterocli.options

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.boolean

class ConfirmationSilencer : OptionGroup() {
    val shouldBeSilent by option("-y")
        .boolean()
        .help("This flag skips confirmation for destructive admin actions")
}