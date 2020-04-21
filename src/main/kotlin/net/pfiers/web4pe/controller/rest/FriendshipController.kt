package net.pfiers.web4pe.controller.rest

import net.pfiers.web4pe.domain.UserPrincipal
import net.pfiers.web4pe.dto.FriendshipDto
import net.pfiers.web4pe.dto.FriendshipDtoPartial
import net.pfiers.web4pe.dto.UserDtoPersisted
import net.pfiers.web4pe.service.UserFriendshipService
import net.pfiers.web4pe.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import javax.validation.Valid

@RestController
@RequestMapping("/api/friendship")
class FriendshipController(@Autowired val userFriendshipService: UserFriendshipService, @Autowired val userService: UserService) {
    @PostMapping
    fun postRequest(mm: ModelMap, @AuthenticationPrincipal userPrincipal: UserPrincipal, @RequestBody @Valid dto: FriendshipDtoPartial): FriendshipDto {
        val requesteeUuid = dto.requestee?.let { userService.get(it) }?.uuid
                ?: throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "No requestee provided")
        return userFriendshipService.requestFriendship(userPrincipal.uuid, requesteeUuid)
    }

    fun UserPrincipal.getUser(): UserDtoPersisted {
        return userService.get(this)
                ?: throw IllegalStateException("User principle doesn't have an associated user")
    }
}