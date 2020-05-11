package net.pfiers.web4pe.chatApp.domain;

import java.io.Serializable
import javax.persistence.*

@Embeddable
@Table(
        uniqueConstraints = [
            UniqueConstraint(columnNames = [
                "requester",
                "requestee"
            ])
        ]
)
class FriendshipId : Serializable {
    constructor()

    constructor(requester: User, requestee: User) {
        this.requester = requester
        this.requestee = requestee
    }

    @ManyToOne
    var requester: User? = null

    @ManyToOne
    var requestee: User? = null


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendshipId

        if (requester != other.requester) return false
        if (requestee != other.requestee) return false

        return true
    }

    override fun hashCode(): Int {
        var result = requester?.hashCode() ?: 0
        result = 31 * result + (requestee?.hashCode() ?: 0)
        return result
    }
}
