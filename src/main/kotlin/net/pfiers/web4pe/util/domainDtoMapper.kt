package net.pfiers.web4pe.util

import net.pfiers.web4pe.domain.Friendship
import net.pfiers.web4pe.domain.Message
import net.pfiers.web4pe.domain.User
import net.pfiers.web4pe.dto.*

fun User.toDto(): UserDtoPersisted = UserDtoPersisted(uuid!!, username!!, password!!, name!!, lastName, status)

fun User.toSelectionDto(): UserSelectionDto = UserSelectionDto(uuid!!)

fun UserDto.toUser(): User = User().update(this)

fun User.update(dto: UserDto): User {
    username = dto.username
    password = dto.password
    name = dto.name
    lastName = dto.lastName
    status = dto.status?: status
    return this
}

// Partial update; keep old values if new not supplied
fun User.update(dto: UserDtoPartial): User {
    username = dto.username ?: username
    name = dto.name ?: name
    lastName = dto.lastName ?: name
    status = dto.status?: status ?: status
    return this
}


fun Friendship.toDto() = FriendshipDto(requester!!.toDto(), requestee!!.toDto())

fun Friendship.toSelectionDto() = FriendshipSelectionDto(requester!!.toSelectionDto(), requestee!!.toSelectionDto())

fun Message.toDto() = MessageDto(sender.toDto(), receiver.toDto(), text)
