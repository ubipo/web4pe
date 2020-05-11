package net.pfiers.web4pe.chatApp.domain

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToOne
    lateinit var sender: User

    @ManyToOne
    lateinit var receiver: User

    @NotNull
    lateinit var text: String
}
