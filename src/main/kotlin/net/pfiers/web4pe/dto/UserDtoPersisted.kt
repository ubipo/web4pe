package net.pfiers.web4pe.dto

import java.util.*

class UserDtoPersisted : UserDto {
    constructor()

    constructor(uuid: UUID, username: String, password: String, name: String, lastName: String? = null, status: String?)
            : super(username, password, name, lastName, status) {
        this.uuid = uuid
    }

    lateinit var uuid: UUID
}