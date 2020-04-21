package net.pfiers.web4pe.service

import net.pfiers.web4pe.domain.Friendship
import net.pfiers.web4pe.domain.User
import net.pfiers.web4pe.dto.FriendshipDto
import net.pfiers.web4pe.dto.UserDtoPersisted
import net.pfiers.web4pe.repo.FriendshipRepo
import net.pfiers.web4pe.repo.UserRepo
import net.pfiers.web4pe.util.toDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserFriendshipService(@Autowired private val userRepo: UserRepo, @Autowired private val friendshipRepo: FriendshipRepo) {
    fun requestFriendship(requester: UserDtoPersisted, requestee: UserDtoPersisted): FriendshipDto {
        return requestFriendship(requester.uuid, requestee.uuid)
    }

    fun requestFriendship(requesterUuid: UUID, requesteeUuid: UUID): FriendshipDto {
        val requesterUser = userRepo.findByUuid(requesterUuid)
                ?: throw NoSuchElementException("Requester with uuid=$requesterUuid")
        val requesteeUser = userRepo.findByUuid(requesteeUuid)
                ?: throw NoSuchElementException("Requestee with uuid=$requesteeUuid")
        return requestFriendship(requesterUser, requesteeUser)
    }

    fun requestFriendship(requester: User, requestee: User): FriendshipDto {
        return friendshipRepo.save(Friendship(requester, requestee)).toDto()
    }

    fun getFriendships(uuid: UUID): List<FriendshipDto> {
        return userRepo.findByUuid(uuid)?.let { friendshipRepo.findByRequesteeOrRequester(it) }?.map { it.toDto() }
                ?: throw NoSuchElementException("User with uuid=$uuid")
    }

    fun getFriendshipsUsername(username: String): List<FriendshipDto> {
        return userRepo.findByUsername(username)?.let { friendshipRepo.findByRequesteeOrRequester(it) }?.map { it.toDto() }
                ?: throw NoSuchElementException("User with username=$username")
    }
}