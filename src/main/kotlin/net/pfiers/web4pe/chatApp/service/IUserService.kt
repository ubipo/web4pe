package net.pfiers.web4pe.chatApp.service

import net.pfiers.web4pe.chatApp.dto.*
import java.util.*

interface IUserService {
    fun get(): List<UserDtoPersisted>

    fun get(username: String): UserDtoPersisted?

    fun get(uuid: UUID): UserDtoPersisted?

    fun get(vagueUserSelectionDto: VagueUserSelectionDto): UserDtoPersisted?

    fun add(dto: UserDto): UserDtoPersisted

    fun add(dto: UserDtoSingup): UserDtoPersisted

    fun update(dto: UserDtoPartial): UserDtoPersisted
}
