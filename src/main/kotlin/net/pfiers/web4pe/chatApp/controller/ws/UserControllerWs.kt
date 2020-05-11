package net.pfiers.web4pe.chatApp.controller.ws

import net.pfiers.web4pe.chatApp.dto.CommentDto
import net.pfiers.web4pe.chatApp.dto.UserDtoPartial
import net.pfiers.web4pe.chatApp.dto.UserDtoPersisted
import net.pfiers.web4pe.chatApp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.util.*
import javax.validation.Valid

@Controller
class UserControllerWs(@Autowired private val userService: UserService) {
    @MessageMapping("/user/broadcast-all")
    @SendTo("/app/user")
    fun get(): List<UserDtoPersisted> {
        return userService.get()
    }

    @MessageMapping("/user/{uuid}")
    fun put(@DestinationVariable uuid: UUID, @Valid dto: UserDtoPartial) {
        userService.update(dto)
    }
}
