package net.pfiers.web4pe.chatApp.controller

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/chat")
class ChatController {
    @GetMapping
    fun getChat(m: ModelMap, auth: Authentication): ModelAndView {
        m.addAttribute("chatActiveCls", "nav-link--active")
        return ModelAndView("chat", m)
    }
}
