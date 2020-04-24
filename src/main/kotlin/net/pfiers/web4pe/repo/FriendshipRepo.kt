package net.pfiers.web4pe.repo

import net.pfiers.web4pe.domain.Friendship
import net.pfiers.web4pe.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FriendshipRepo : JpaRepository<Friendship, Long> {
    fun findByIdRequesterAndIdRequestee(requester: User, requestee: User): Friendship

    @Query(
            "select f from #{#entityName} f where " +
                    "(f.id.requester = :requester and f.id.requestee = :requestee) or (f.id.requester = :requestee and f.id.requestee = :requester)"
    )
    fun findByRequesterAndRequesteeOrInverse(requester: User, requestee: User): Friendship?

    @Query("select f from #{#entityName} f where f.id.requester = :user or f.id.requestee = :user")
    fun findByRequesteeOrRequester(user: User): List<Friendship>
}