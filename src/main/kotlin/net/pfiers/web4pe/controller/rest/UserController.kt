package net.pfiers.web4pe.controller.rest

import net.pfiers.web4pe.domain.UserPrincipal
import net.pfiers.web4pe.dto.UserDto
import net.pfiers.web4pe.dto.UserDtoPartial
import net.pfiers.web4pe.dto.UserDtoPersisted
import net.pfiers.web4pe.service.UserFriendshipService
import net.pfiers.web4pe.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/user")
class UserController(@Autowired val userService: UserService, @Autowired val userFriendshipService: UserFriendshipService) {
    @GetMapping("self")
    fun getSelf(mm: ModelMap, @AuthenticationPrincipal userPrincipal: UserPrincipal): UserDto? {
        return userService.get(userPrincipal)
    }

    @PutMapping("self")
    fun putSelf(mm: ModelMap, @AuthenticationPrincipal userPrincipal: UserPrincipal, @RequestBody @Valid dto: UserDtoPartial): UserDto {
        dto.uuid = userPrincipal.getUser().uuid // Override or set, should actually check credentials
        return userService.update(dto)
    }

    @GetMapping("{uuid}")
    fun get(mm: ModelMap, @AuthenticationPrincipal userPrincipal: UserPrincipal, @PathVariable uuid: UUID): UserDto? {
        return userService.get(userPrincipal)
    }

    @GetMapping("self/friends")
    fun getSelfFriendships(mm: ModelMap, @AuthenticationPrincipal userPrincipal: UserPrincipal): List<UserDto> {
        val uuid = userPrincipal.getUser().uuid
        return userFriendshipService.getFriendships(uuid).map {
            it.getOther(uuid)
        }
    }

    fun UserPrincipal.getUser(): UserDtoPersisted {
        return userService.get(this)
                ?: throw IllegalStateException("User principle doesn't have an associated user")
    }
}