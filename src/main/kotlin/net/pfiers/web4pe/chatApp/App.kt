package net.pfiers.web4pe.chatApp

import net.pfiers.web4pe.chatApp.dto.UserDto
import net.pfiers.web4pe.chatApp.service.UserFriendshipService
import net.pfiers.web4pe.chatApp.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.annotation.PostConstruct

@SpringBootApplication
class App(@Autowired val userService: UserService, @Autowired val userFriendshipService: UserFriendshipService) {
	@PostConstruct
	fun init() {
		val pass = BCryptPasswordEncoder().encode("t")
		val bib = userService.add(UserDto("bib@ucll.be", pass, "Bib", status = "away"))
		val jan = userService.add(UserDto("jan@ucll.be", pass, "Jan", "Janssens", status = "online"))
		val an = userService.add(UserDto("an@ucll.be", pass, "An", "Cornelissen", gender = "f"))
		userFriendshipService.requestFriendship(bib, jan)
		userFriendshipService.requestFriendship(bib, an)
	}
}

fun main(args: Array<String>) {
	runApplication<App>(*args)
}
