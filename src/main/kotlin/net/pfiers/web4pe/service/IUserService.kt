package net.pfiers.web4pe.service

import net.pfiers.web4pe.dto.UserDto
import net.pfiers.web4pe.dto.UserDtoPartial
import net.pfiers.web4pe.dto.UserDtoPersisted
import net.pfiers.web4pe.dto.VagueUserSelectionDto
import java.util.*

interface IUserService {
    fun get(username: String): UserDtoPersisted?

    fun get(uuid: UUID): UserDtoPersisted?

    fun get(vagueUserSelectionDto: VagueUserSelectionDto): UserDtoPersisted?

    fun add(dto: UserDto): UserDtoPersisted

    fun update(dto: UserDtoPartial): UserDtoPersisted
}