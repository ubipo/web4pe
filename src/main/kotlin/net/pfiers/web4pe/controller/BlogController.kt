package net.pfiers.web4pe.controller

import net.pfiers.web4pe.dto.PostDto
import net.pfiers.web4pe.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/blog")
class BlogController(@Autowired private val postService: PostService) {
    @GetMapping
    fun get(m: ModelMap): ModelAndView {
        val posts = postService.getAll()
        m.addAttribute("posts", posts)
        m.addAttribute("blogActiveCls", "nav-link--active")
        return ModelAndView("blog", m)
    }
}