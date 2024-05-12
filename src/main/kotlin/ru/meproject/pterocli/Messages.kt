package ru.meproject.pterocli

import ru.meproject.pterocli.commands.client.PowerActionCommand
import ru.meproject.pterocli.commands.client.SendCommand


// Various Admin Commands
const val UpdateStartupCommandParameterHelp = "New startup command to set on server"

const val UpdateImageParameterHelp = "URL of container to set on server"

const val GeneralAdminSkipConfirmationHelp = "This flag skips confirmation for destructive admin actions"

// Various Client commands

const val DownloadFileRemoteFileHelp = "Remote path to file to download"



const val GeneralServerIdsHelp = "List of short server IDs to perform command on"

fun updateStartupConfirmation(pServer: String) = "You sure you want to change startup command on server $pServer?"

fun updateContainerConfirmation(pServer: String) = "You sure you want to change container on server $pServer?"