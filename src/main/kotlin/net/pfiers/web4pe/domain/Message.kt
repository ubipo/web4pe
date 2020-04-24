package net.pfiers.web4pe.domain

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToOne
    lateinit var friendship: Friendship

    @NotNull
    lateinit var text: String
}