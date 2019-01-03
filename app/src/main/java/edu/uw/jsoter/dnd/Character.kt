package edu.uw.jsoter.dnd

class Character(name: String) {
    val attributes = mutableMapOf<String, String>()
    val name = name

    fun addToAttributes(attribute: String) {
        attributes[attribute] = ""
    }

}

