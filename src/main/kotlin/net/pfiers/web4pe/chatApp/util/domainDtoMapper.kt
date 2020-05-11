package net.pfiers.web4pe.chatApp.util

import net.pfiers.web4pe.chatApp.domain.Friendship
import net.pfiers.web4pe.chatApp.domain.Message
import net.pfiers.web4pe.chatApp.domain.User
import net.pfiers.web4pe.chatApp.dto.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun User.toDto(): UserDtoPersisted = UserDtoPersisted(uuid!!, username!!, password!!, name!!, lastName, gender, status, age)

fun User.toSelectionDto(): UserSelectionDto = UserSelectionDto(uuid!!)

fun UserDto.toUser(): User = User().update(this)

fun UserDtoSingup.toUser(): User {
    val user = User()
    user.username = username
    user.password = BCryptPasswordEncoder().encode(passwordText)
    user.name = name
    user.lastName = lastName
    user.gender = gender
    user.status = status
    user.age = age
    return user
}

fun User.update(dto: UserDto): User {
    username = dto.username
    password = dto.password
    name = dto.name
    lastName = dto.lastName
    gender = dto.gender
    status = dto.status
    age = dto.age
    return this
}

// Partial update; keep old values if new not supplied
fun User.update(dto: UserDtoPartial): User {
    username = dto.username ?: username
    name = dto.name ?: name
    lastName = dto.lastName ?: name
    gender = dto.gender ?: gender
    status = dto.status ?: status
    age = dto.age ?: age
    return this
}


fun Friendship.toDto() = FriendshipDto(requester!!.toDto(), requestee!!.toDto())

fun Friendship.toSelectionDto() = FriendshipSelectionDto(requester!!.toSelectionDto(), requestee!!.toSelectionDto())

fun Message.toDto() = MessageDto(sender.toDto(), receiver.toDto(), text)
