package net.pfiers.web4pe.service

import net.pfiers.web4pe.domain.User
import net.pfiers.web4pe.repo.FriendshipRepo
import net.pfiers.web4pe.util.toDto
import org.springframework.beans.factory.annotation.Autowired

class FriendshipService(@Autowired private val friendshipRepo: FriendshipRepo) {
    fun getByRequesterOrRequestee(user: User) {
        friendshipRepo.findByRequesteeOrRequester(user).map { it.toDto() }
    }
}