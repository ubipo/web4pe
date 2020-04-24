package net.pfiers.web4pe.dto

import java.util.*
import javax.validation.constraints.*
import kotlin.properties.Delegates

class CommentDto {
    @NotBlank
    lateinit var name: String
    @NotBlank
    lateinit var comment: String
    @Min(0)
    @Max(5)
    var rating = 0
    @NotNull
    lateinit var postUuid: UUID

    override fun toString(): String {
        return "CommentDto(name='$name', comment='$comment', rating='$rating', postUuid='$postUuid')"
    }
}