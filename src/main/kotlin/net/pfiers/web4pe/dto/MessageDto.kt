package net.pfiers.web4pe.dto

class MessageDto() {
    constructor(friendshipSelectionDto: FriendshipSelectionDto, text: String) : this() {
        this.friendshipSelectionDto = friendshipSelectionDto
        this.text = text
    }

    lateinit var friendshipSelectionDto: FriendshipSelectionDto
    lateinit var text: String
}