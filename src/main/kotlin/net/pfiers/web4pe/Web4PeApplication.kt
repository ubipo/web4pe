package net.pfiers.web4pe

import net.pfiers.web4pe.dto.UserDto
import net.pfiers.web4pe.service.UserFriendshipService
import net.pfiers.web4pe.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.annotation.PostConstruct

@SpringBootApplication
class Web4PeApplication(@Autowired val userService: UserService, @Autowired val userFriendshipService: UserFriendshipService) {
	@PostConstruct
	fun init() {
		val pass = BCryptPasswordEncoder().encode("t")
		val bib = userService.add(UserDto("bib@ucll.be", pass, "Bib", status = "away"))
		val jan = userService.add(UserDto("jan@ucll.be", pass, "Jan", "Janssens","online"))
		val an = userService.add(UserDto("an@ucll.be", pass, "An", "Cornelissen"))
		userFriendshipService.requestFriendship(bib, jan)
		userFriendshipService.requestFriendship(bib, an)
	}
}

fun main(args: Array<String>) {
	runApplication<Web4PeApplication>(*args)
}
