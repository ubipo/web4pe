package net.pfiers.web4pe.chatApp.dto

class FriendshipSelectionDto() {
    constructor(requester: UserSelectionDto, requestee: UserSelectionDto) : this() {
        this.requester = requester
        this.requestee = requestee
    }

    lateinit var requester: UserSelectionDto
    lateinit var requestee: UserSelectionDto
}
