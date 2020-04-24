package net.pfiers.web4pe.service

import net.pfiers.web4pe.domain.UserPrincipal
import net.pfiers.web4pe.dto.UserDto
import net.pfiers.web4pe.dto.UserDtoPartial
import net.pfiers.web4pe.dto.UserDtoPersisted
import net.pfiers.web4pe.dto.VagueUserSelectionDto
import net.pfiers.web4pe.repo.UserRepo
import net.pfiers.web4pe.util.toDto
import net.pfiers.web4pe.util.toUser
import net.pfiers.web4pe.util.update
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException

@Service
@Transactional
class UserService(@Autowired private val userRepo: UserRepo) : IUserService {
    override fun get(username: String): UserDtoPersisted? {
        return userRepo.findByUsername(username)?.toDto()
    }

    override fun get(uuid: UUID): UserDtoPersisted? {
        return userRepo.findByUuid(uuid)?.toDto()
    }

    override fun get(vagueUserSelectionDto: VagueUserSelectionDto): UserDtoPersisted? {
        val uuid = vagueUserSelectionDto.uuid
        val username = vagueUserSelectionDto.username
        return when {
            uuid != null -> get(uuid)
            username != null -> get(username)
            else -> throw NoSuchElementException("User with uuid=/ && username=/")
        }
    }

    override fun add(dto: UserDto): UserDtoPersisted {
        val user = when(dto) {
            is UserDtoPersisted -> userRepo.findByUuid(dto.uuid)?.update(dto)
                    ?: throw NoSuchElementException("User with uuid=${dto.uuid}")
            else -> dto.toUser()
        }

        return userRepo.save(user).toDto()
    }

    override fun update(dto: UserDtoPartial): UserDtoPersisted {
        val user = userRepo.findByUuid(dto.uuid)?.update(dto)
                ?: throw NoSuchElementException("User with uuid=${dto.uuid}")
        return userRepo.save(user).toDto()
    }

    fun get(userPrincipal: UserPrincipal): UserDtoPersisted? {
        return get(userPrincipal.uuid)
    }
}