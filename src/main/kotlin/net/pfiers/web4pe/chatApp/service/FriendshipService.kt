package net.pfiers.web4pe.chatApp.service

import net.pfiers.web4pe.chatApp.domain.User
import net.pfiers.web4pe.chatApp.repo.FriendshipRepo
import net.pfiers.web4pe.chatApp.util.toDto
import org.springframework.beans.factory.annotation.Autowired

class FriendshipService(@Autowired private val friendshipRepo: FriendshipRepo) {
    fun getByRequesterOrRequestee(user: User) {
        friendshipRepo.findByRequesteeOrRequester(user).map { it.toDto() }
    }
}
