package net.pfiers.web4pe.chatApp.dto

class ReceivedMessageDto() {
    constructor(sender: UserSelectionDto, receiver: UserSelectionDto, text: String) : this() {
        this.sender = sender
        this.receiver = receiver
        this.text = text
    }

    lateinit var sender: UserSelectionDto
    lateinit var receiver: UserSelectionDto
    lateinit var text: String
}
