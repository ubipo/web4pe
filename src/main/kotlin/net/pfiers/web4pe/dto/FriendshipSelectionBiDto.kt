package net.pfiers.web4pe.dto

class FriendshipSelectionBiDto() {
    constructor(a: UserSelectionDto, b: UserSelectionDto) : this() {
        this.friendA = a
        this.friendB = b
    }

    lateinit var friendA: UserSelectionDto
    lateinit var friendB: UserSelectionDto
}