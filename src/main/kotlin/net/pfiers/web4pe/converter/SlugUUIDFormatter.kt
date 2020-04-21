package net.pfiers.web4pe.converter

import org.springframework.format.Formatter
import java.nio.ByteBuffer
import java.util.*

class SlugUUIDFormatter : Formatter<UUID> {
    override fun parse(slug: String, locale: Locale): UUID {
        return parse(slug)
    }

    override fun print(uuid: UUID, locale: Locale): String {
        return print(uuid)
    }

    companion object {
        private val encoder = Base64.getUrlEncoder()
        private val decoder = Base64.getUrlDecoder()
        fun parse(slug: String?): UUID {
            return try {
                UUID.fromString(slug)
            } catch (ex: IllegalArgumentException) {
                // Not a UUID, probably a slug
                fromBytes(decoder.decode(slug))
            }
        }

        fun print(uuid: UUID): String {
            return encoder.encodeToString(fromBytes(uuid)).substring(0, 22)
        }

        private fun fromBytes(bytes: ByteArray): UUID {
            require(bytes.size == 16) { "<bytes> array must be length 16" }
            val bb = ByteBuffer.wrap(bytes)
            val high = bb.long
            val low = bb.long
            return UUID(high, low)
        }

        private fun fromBytes(uuid: UUID): ByteArray {
            val bb = ByteBuffer.allocate(16)
            bb.putLong(uuid.mostSignificantBits)
            bb.putLong(uuid.leastSignificantBits)
            return bb.array()
        }
    }
}
