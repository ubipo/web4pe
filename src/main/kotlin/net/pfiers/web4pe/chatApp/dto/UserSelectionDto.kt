package net.pfiers.web4pe.chatApp.dto

import java.util.*

class UserSelectionDto() {
    constructor(uuid: UUID) : this() {
        this.uuid = uuid
    }

    lateinit var uuid: UUID
}
