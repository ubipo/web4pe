package net.pfiers.web4pe.controller.rest

import net.pfiers.web4pe.dto.MessageDto
import net.pfiers.web4pe.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RequestMapping("/api/message")
class MessageController(private val messageService: MessageService) {
    @GetMapping
    fun get(@RequestParam(required = false) userUuid: UUID?): List<MessageDto> {
        if (userUuid == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,  "One of: (userUuid) must be specified")
        }
        return messageService.getByUser(userUuid)
    }
}