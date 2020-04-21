package net.pfiers.web4pe.domain

import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @Column(updatable = false, nullable = false)
    var uuid: UUID? = null

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    var created: Timestamp? = null

    @Column(nullable = false, unique = true)
    var username: String? = null

    @NotNull
    var name: String? = null

    var lastName: String? = null

    @NotNull
    var password: String? = null

    var status: String? = null

    @OneToMany(mappedBy = "id.requester", orphanRemoval = true)
    var friendsRequester: List<Friendship>? = null

    @OneToMany(mappedBy = "id.requestee", orphanRemoval = true)
    var friendsRequestee: List<Friendship>? = null

    @PrePersist
    fun onCreate() {
        uuid = UUID.randomUUID()
    }
}