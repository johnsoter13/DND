package edu.uw.jsoter.dnd

class Character(name: String) {
    val attributes = mutableMapOf<String, String>()

    fun addToAttributes(attribute: String) {
        attributes[attribute] = ""
    }

}

