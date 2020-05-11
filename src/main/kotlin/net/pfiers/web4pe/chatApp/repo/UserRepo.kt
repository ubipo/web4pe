package net.pfiers.web4pe.chatApp.repo

import net.pfiers.web4pe.chatApp.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepo : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?

    fun findByUuid(uuid: UUID): User?
}
