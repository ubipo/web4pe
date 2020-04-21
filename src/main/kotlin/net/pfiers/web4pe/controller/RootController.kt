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
    fun getIndex(mm: ModelMap): ModelAndView {
        val m = ModelAndView("index", mm)
        m.addObject("errors", listOf<String>())
        m.addObject("homeActive", "nav-link--active")
        return m
    }
}