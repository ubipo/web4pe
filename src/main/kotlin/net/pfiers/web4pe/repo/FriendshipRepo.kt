package net.pfiers.web4pe.repo

import net.pfiers.web4pe.domain.Friendship
import net.pfiers.web4pe.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FriendshipRepo : JpaRepository<Friendship, Long> {
    fun findByidRequestee(requestee: User)

    fun findByidRequester(requester: User)

    @Query("select f from #{#entityName} f where f.id.requester = :user or f.id.requestee = :user")
    fun findByRequesteeOrRequester(user: User): List<Friendship>
}