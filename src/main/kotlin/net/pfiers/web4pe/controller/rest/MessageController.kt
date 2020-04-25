package net.pfiers.web4pe.controller.rest

import net.pfiers.web4pe.domain.UserPrincipal
import net.pfiers.web4pe.dto.MessageDto
import net.pfiers.web4pe.dto.ReceivedMessageDto
import net.pfiers.web4pe.dto.UserDtoPersisted
import net.pfiers.web4pe.service.MessageService
import net.pfiers.web4pe.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/message")
class MessageController(@Autowired private val messageService: MessageService, @Autowired private val userService: UserService) {
    @GetMapping
    fun get(@RequestParam userUuid: UUID, @RequestParam(required = false) fuzzyText: String?): List<MessageDto> {
        return when (fuzzyText) {
            null -> messageService.getByUser(userUuid)
            else -> messageService.getByUserAndFuzzyText(userUuid, fuzzyText)
        }
    }

    @GetMapping("/self")
    fun getBySelf(@AuthenticationPrincipal userPrincipal: UserPrincipal, @RequestParam(required = false) fuzzyText: String?): List<MessageDto> {
        val uuid = userPrincipal.getUser().uuid
        return when (fuzzyText) {
            null -> messageService.getByUser(uuid)
            else -> messageService.getByUserAndFuzzyText(uuid, fuzzyText)
        }
    }

    @PostMapping
    fun post(@RequestBody @Valid messageDto: ReceivedMessageDto): MessageDto {
        return messageService.add(messageDto)
    }

    fun UserPrincipal.getUser(): UserDtoPersisted {
        return userService.get(this)
                ?: throw IllegalStateException("User principle doesn't have an associated user")
    }
}