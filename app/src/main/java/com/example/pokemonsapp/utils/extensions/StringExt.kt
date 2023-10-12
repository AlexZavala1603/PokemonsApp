package com.example.pokemonsapp.utils.extensions

fun String.startsWithAlphabetCharacter(): Boolean {
    if (this.isNotEmpty()) {
        val firstChar = this[0]
        return (firstChar in 'A'..'Z' || firstChar in 'a'..'z')
    }
    return false
}

fun String.getInitials(): String {
    val words = this.trim().split("\\s+".toRegex())
    return when {
        words.isEmpty() -> ""
        words.size == 1 -> {
            val firstWord = words[0]
            if (firstWord.isNotEmpty()) firstWord[0].toString() else ""
        }

        else -> {
            val firstInitial = words[0].firstOrNull()
            val secondInitial = words[1].firstOrNull()
            val initials = mutableListOf<String>()

            if (firstInitial != null) initials.add(firstInitial.toString())
            if (secondInitial != null) initials.add(secondInitial.toString())

            initials.joinToString("")
        }
    }
}
