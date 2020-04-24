package net.pfiers.web4pe.repo

import net.pfiers.web4pe.domain.Friendship
import net.pfiers.web4pe.domain.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepo : JpaRepository<Message, Long> {
    fun findAllByFriendship(friendship: Friendship): List<Message>
}