package net.pfiers.web4pe.dto

import com.fasterxml.jackson.annotation.JsonIgnore

open class UserDto() {
        constructor(username: String, password: String, name: String, lastName: String? = null, status: String? = null) : this() {
                this.username = username
                this.password = password
                this.name = name
                this.lastName = lastName
                this.status = status
        }

        lateinit var username: String
        @JsonIgnore
        lateinit var password: String
        lateinit var name: String
        var lastName: String? = null
        var status: String? = null
}