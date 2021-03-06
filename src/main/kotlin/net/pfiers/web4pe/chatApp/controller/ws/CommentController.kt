package net.pfiers.web4pe.chatApp.controller.ws

import net.pfiers.web4pe.chatApp.dto.CommentDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import javax.validation.Valid

@Controller
class CommentController {
    @MessageMapping("/comment/create")
    @SendTo("/comment")
    fun receive(@Valid comment: CommentDto): CommentDto {
        return comment
    }
}
