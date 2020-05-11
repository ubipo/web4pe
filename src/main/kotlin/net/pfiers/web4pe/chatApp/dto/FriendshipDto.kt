package net.pfiers.web4pe.chatApp.dto

import java.util.*

class FriendshipDto() {
    constructor(requester: UserDtoPersisted, requestee: UserDtoPersisted) : this() {
        this.requester = requester
        this.requestee = requestee
    }

    lateinit var requester: UserDtoPersisted
    lateinit var requestee: UserDtoPersisted

    fun getOther(uuid: UUID): UserDto {
        return when (uuid) {
            requester.uuid -> requestee
            requestee.uuid -> requester
            else -> throw Exception("Neither requester nor requestee have the provided UUID")
        }
    }
}
