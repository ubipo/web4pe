package net.pfiers.web4pe.service

import net.pfiers.web4pe.dto.MessageDto
import net.pfiers.web4pe.repo.FriendshipRepo
import net.pfiers.web4pe.repo.MessageRepo
import net.pfiers.web4pe.repo.UserRepo
import net.pfiers.web4pe.util.toDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(@Autowired private val messageRepo: MessageRepo, @Autowired private val userRepo: UserRepo, @Autowired private val friendshipRepo: FriendshipRepo) {
    fun getByUser(uuid: UUID): List<MessageDto> {
        val friendships = userRepo.findByUuid(uuid)?.let { friendshipRepo.findByRequesteeOrRequester(it) }
                ?: throw NoSuchElementException("User with uuid=$uuid")
        val messages = friendships.map{ messageRepo.findAllByFriendship(it).map { it.toDto() } }.flatten()
        return messages
    }
}