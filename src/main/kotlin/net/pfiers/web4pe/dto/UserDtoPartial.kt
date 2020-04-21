package net.pfiers.web4pe.dto

import java.util.*

class UserDtoPartial {
    lateinit var uuid: UUID

    var username: String? = null
    var name: String? = null
    var lastName: String? = null
    var status: String? = null
}