package net.pfiers.web4pe.chatApp.service

import net.pfiers.web4pe.chatApp.dto.PostDto
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class PostService {
    lateinit var posts: List<PostDto>

    @PostConstruct
    fun init() {
        @Suppress("SpellCheckingInspection")
        this.posts = listOf(
                PostDto("Waar volg je volgend jaar een stage?"),
                PostDto("Was het een interessante projectweek?"),
                PostDto("Wat ben je van plan om te doen vandaag?"),
                PostDto("Naar welke muziek ben je momenteel aan het luisteren?"),
                PostDto("Wat zijn de examenvragen voor het vak Web4?")
        )
    }

    fun getAll(): List<PostDto> {
        return posts
    }
}
