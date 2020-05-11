package net.pfiers.web4pe.chatApp.service

import net.pfiers.web4pe.chatApp.domain.UserPrincipal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(@Autowired val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userService.get(username) ?: throw UsernameNotFoundException(username)
        return UserPrincipal(user)
    }
}
