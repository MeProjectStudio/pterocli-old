package ru.meproject.pterocli.options

import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.types.long

class AdminServerIds : OptionGroup() {
    val ids: List<Long> by option("-s", "--servers")
        .long()
        .split(",")
        .required()
        .help("List of numeric IDs of servers (numbers from admin panel, not UUIDs)")
}