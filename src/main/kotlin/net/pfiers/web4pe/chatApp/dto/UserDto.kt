package net.pfiers.web4pe.chatApp.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

open class UserDto() {
        constructor(username: String, password: String,
                    name: String, lastName: String? = null,
                    gender: String? = null, status: String? = null,
                    age: Int? = null) : this() {
                this.username = username
                this.password = password
                this.name = name
                this.lastName = lastName
                this.gender = gender
                this.status = status
        }

        lateinit var username: String
        @JsonIgnore
        lateinit var password: String
        @NotBlank
        lateinit var name: String
        var lastName: String? = null
        var gender: String? = null
        var status: String? = null
        @Min(0)
        @Max(150)
        var age: Int? = null
}
