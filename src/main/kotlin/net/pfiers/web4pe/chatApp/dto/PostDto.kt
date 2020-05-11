package net.pfiers.web4pe.chatApp.dto

import java.util.*

class PostDto(val title: String) {
    val uuid: UUID = UUID.randomUUID()
}
