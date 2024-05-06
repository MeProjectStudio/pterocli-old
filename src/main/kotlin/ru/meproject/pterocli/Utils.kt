package ru.meproject.pterocli

internal fun extractParent(arrayedPath: Array<String>): String {
    val stringBuilder = StringBuilder()
    for (i in 0 until arrayedPath.size - 1) {
        stringBuilder.append(arrayedPath[i])
        if (i < arrayedPath.size - 2) {
            stringBuilder.append("/")
        }
    }
    return stringBuilder.toString()
}