package net.pfiers.web4pe.chatApp.service

import net.pfiers.web4pe.chatApp.domain.UserPrincipal
import net.pfiers.web4pe.chatApp.dto.*
import net.pfiers.web4pe.chatApp.repo.UserRepo
import net.pfiers.web4pe.chatApp.util.toDto
import net.pfiers.web4pe.chatApp.util.toUser
import net.pfiers.web4pe.chatApp.util.update
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.core.MessageSendingOperations
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException

@Service
@Transactional
class UserService(@Autowired private val userRepo: UserRepo, @Autowired private val messagingTemplate: SimpMessagingTemplate) : IUserService {
    override fun get(): List<UserDtoPersisted> {
        return userRepo.findAll().map { it.toDto() }
    }

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
        val persisted = userRepo.save(user)
        broadcastUsers()
        return persisted.toDto()
    }

    override fun add(dto: UserDtoSingup): UserDtoPersisted {
        val persisted = userRepo.save(dto.toUser())
        broadcastUsers()
        return persisted.toDto()
    }

    override fun update(dto: UserDtoPartial): UserDtoPersisted {
        val user = userRepo.findByUuid(dto.uuid)?.update(dto)
                ?: throw NoSuchElementException("User with uuid=${dto.uuid}")
        val persisted = userRepo.save(user)
        broadcastUsers()
        return persisted.toDto()
    }

    fun get(userPrincipal: UserPrincipal): UserDtoPersisted? {
        return get(userPrincipal.uuid)
    }

    fun broadcastUsers() {
        messagingTemplate.convertAndSend("/app/user", get())
    }
}
