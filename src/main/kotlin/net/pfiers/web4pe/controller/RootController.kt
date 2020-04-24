package net.pfiers.web4pe.controller

import net.pfiers.web4pe.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/")
class RootController(@Autowired val userService: UserService) {
    @GetMapping
    fun getIndex(m: ModelMap): ModelAndView {
        m.addAttribute("errors", listOf<String>())
        m.addAttribute("homeActiveCls", "nav-link--active")
        return ModelAndView("index", m)
    }
}