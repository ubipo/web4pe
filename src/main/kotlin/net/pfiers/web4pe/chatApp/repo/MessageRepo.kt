package net.pfiers.web4pe.chatApp.repo

import net.pfiers.web4pe.chatApp.domain.Message
import net.pfiers.web4pe.chatApp.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MessageRepo : JpaRepository<Message, Long> {
    fun findAllBySender(sender: User): List<Message>

    fun findAllByReceiver(sender: User): List<Message>

    @Query("select m from #{#entityName} m " +
            "where m.sender = :senderOrReceiver or m.receiver = :senderOrReceiver")
    fun findAllBySenderOrReceiver(senderOrReceiver: User): List<Message>

    @Query("select m from #{#entityName} m " +
            "where (m.sender = :senderOrReceiver or m.receiver = :senderOrReceiver) and LOWER(m.text) like CONCAT('%', LOWER(:fuzzyText), '%')")
    fun findAllBySenderOrReceiverAndFuzzyText(senderOrReceiver: User, fuzzyText: String): List<Message>
}
