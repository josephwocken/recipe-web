package recipeweb

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

class MapperUtil {

    companion object {
        private val mapper = ObjectMapper()
                .registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .registerModule(Jdk8Module())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)

        fun getObjectMapper(): ObjectMapper {
            return mapper
        }
    }
}