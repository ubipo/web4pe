package net.pfiers.web4pe.service

import net.pfiers.web4pe.domain.Message
import net.pfiers.web4pe.dto.MessageDto
import net.pfiers.web4pe.dto.ReceivedMessageDto
import net.pfiers.web4pe.repo.FriendshipRepo
import net.pfiers.web4pe.repo.MessageRepo
import net.pfiers.web4pe.repo.UserRepo
import net.pfiers.web4pe.util.toDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class MessageService(@Autowired private val messageRepo: MessageRepo, @Autowired private val userRepo: UserRepo, @Autowired private val friendshipRepo: FriendshipRepo) {
    fun getByUser(uuid: UUID): List<MessageDto> {
        val user = userRepo.findByUuid(uuid)
                ?: throw NoSuchElementException("User with uuid=$uuid")
        return messageRepo.findAllBySenderOrReceiver(user).map { it.toDto() }
    }

    fun add(messageDto: ReceivedMessageDto): MessageDto {
        val message = Message()
        message.text = messageDto.text
        val senderUuid = messageDto.sender.uuid
        message.sender = userRepo.findByUuid(senderUuid)
                ?: throw NoSuchElementException("Sender user with uuid=${senderUuid}")
        val requesteeUuid = messageDto.receiver.uuid
        message.receiver = userRepo.findByUuid(requesteeUuid)
                ?: throw NoSuchElementException("Receiver user with uuid=${requesteeUuid}")
        return messageRepo.save(message).toDto()
    }
}