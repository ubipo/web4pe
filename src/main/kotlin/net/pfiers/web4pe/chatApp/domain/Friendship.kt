package net.pfiers.web4pe.chatApp.domain

import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
class Friendship {
    constructor()

    constructor(requestee: User, requester: User, accepted: Boolean = false) {
        this.id = FriendshipId(requester, requestee)
        if (accepted)
            this.acceptanceTime = Timestamp.from(Instant.now())
    }

    @EmbeddedId
    var id: FriendshipId? = null

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    var creationTime: Timestamp? = null

    var acceptanceTime: Timestamp? = null

    var requester: User?
        get() = id?.requester
        set(value) {
            id?.requester = value
        }

    var requestee: User?
        get() = id?.requestee
        set(value) {
            id?.requestee = value
        }
}
