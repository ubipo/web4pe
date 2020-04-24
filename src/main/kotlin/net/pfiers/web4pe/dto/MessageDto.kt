package net.pfiers.web4pe.dto

class MessageDto() {
    constructor(sender: UserDto, receiver: UserDto, text: String) : this() {
        this.sender = sender
        this.receiver = receiver
        this.text = text
    }

    lateinit var sender: UserDto
    lateinit var receiver: UserDto
    lateinit var text: String
}