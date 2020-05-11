package net.pfiers.web4pe.chatApp.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

open class UserDtoSingup() {
        constructor(username: String, passwordText: String,
                    passwordTextRepeated: String, name: String,
                    lastName: String? = null, gender: String? = null,
                    status: String? = null, age: Int? = null) : this() {
                this.username = username
                this.passwordText = passwordText
                this.passwordTextRepeated = passwordTextRepeated
                this.name = name
                this.lastName = lastName
                this.gender = gender
                this.status = status
                this.age = age
        }

        @NotBlank
        var username: String? = null
        @NotBlank
        var passwordText: String? = null
        @NotBlank
        var passwordTextRepeated: String? = null
        @NotBlank
        var name: String? = null
        var lastName: String? = null
        var gender: String? = null
        var status: String? = null
        @Min(0)
        @Max(150)
        var age: Int? = null
}
