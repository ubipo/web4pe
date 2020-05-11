package net.pfiers.web4pe.chatApp.controller

import net.pfiers.web4pe.chatApp.dto.UserDto
import net.pfiers.web4pe.chatApp.dto.UserDtoSingup
import net.pfiers.web4pe.chatApp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView
import javax.validation.Valid

@Controller
@RequestMapping("/")
class RootController(@Autowired val userService: UserService) {
    @GetMapping
    fun getIndex(m: ModelMap, @RequestParam(required = false) credError: Boolean?): ModelAndView {
        val errors = if (credError == true) listOf<String>("Bad email and/or password") else listOf<String>()
        m.addAttribute("errors", errors)
        m.addAttribute("homeActiveCls", "nav-link--active")
        return ModelAndView("index", m)
    }

    @GetMapping("signup")
    fun getSignup(m: ModelMap): ModelAndView {
        m.addAttribute("errors", listOf<String>())
        m.addAttribute("homeActiveCls", "nav-link--active")
        m.addAttribute("user", UserDtoSingup())
        return ModelAndView("signup", m)
    }

    @PostMapping("signup")
    fun postSignup(m: ModelMap, @ModelAttribute("user") @Valid dto: UserDtoSingup, bindingResult: BindingResult): ModelAndView {
        if (dto.passwordText != dto.passwordTextRepeated)
            bindingResult.addError(ObjectError("user", "Passwords do not match"))
        if (bindingResult.hasErrors()) {
            m.addAttribute("errors", listOf<String>())
            m.addAttribute("homeActiveCls", "nav-link--active")
            m.addAttribute("user", dto)
            val mv = ModelAndView("signup", m)
            mv.status = HttpStatus.BAD_REQUEST
            return mv
        }
        userService.add(dto)
        return ModelAndView(RedirectView("/"), m)
    }
}
