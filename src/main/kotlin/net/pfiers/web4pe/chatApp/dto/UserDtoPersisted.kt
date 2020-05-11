package net.pfiers.web4pe.chatApp.dto

import java.util.*

class UserDtoPersisted : UserDto {
    constructor()

    constructor(uuid: UUID, username: String, password: String,
                name: String, lastName: String? = null,
                gender: String? = null, status: String? = null,
                age: Int? = null)
            : super(username, password, name, lastName, gender, status, age) {
        this.uuid = uuid
    }

    lateinit var uuid: UUID
}
