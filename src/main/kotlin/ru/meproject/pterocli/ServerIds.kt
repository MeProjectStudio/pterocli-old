package ru.meproject.pterocli

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.varargValues

class ServerIds: OptionGroup() {
    val ids: List<String> by option("-s", "--servers")
        .varargValues()
        .required()
        .help("List of servers to perform command against")
}