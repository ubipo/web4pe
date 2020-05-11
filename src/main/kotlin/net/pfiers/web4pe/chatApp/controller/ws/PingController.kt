package net.pfiers.web4pe.chatApp.controller.ws

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class PingController() {
    @MessageMapping("/ping")
    @SendTo("/ping")
    fun receive(data: String): String {
        return "Pong!"
    }
}
