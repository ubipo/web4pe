package net.pfiers.web4pe.controller.ws

import net.pfiers.web4pe.dto.CommentDto
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class CommentController {
    @MessageMapping("/comment/create")
    @SendTo("/comment")
    fun receive(comment: CommentDto): CommentDto {
        return comment
    }
}