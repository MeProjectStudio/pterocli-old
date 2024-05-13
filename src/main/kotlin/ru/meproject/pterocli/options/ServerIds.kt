package ru.meproject.pterocli.options

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.*
import ru.meproject.pterocli.GeneralServerIdsHelp

class ServerIds: OptionGroup() {
    val ids: List<String> by option("-s", "--servers")
        .split(",")
        .required()
        .help(GeneralServerIdsHelp)
}