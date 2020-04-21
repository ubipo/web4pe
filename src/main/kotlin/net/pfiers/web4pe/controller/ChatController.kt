package net.pfiers.web4pe.controller

import net.pfiers.web4pe.domain.UserPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.security.Principal

@Controller
@RequestMapping("/chat")
class ChatController {
    @GetMapping
    fun getChat(mm: ModelMap, auth: Authentication): ModelAndView {
        val m = ModelAndView("chat", mm)
        m.addObject("chatActive", "nav-link--active")
        return m
    }
}
