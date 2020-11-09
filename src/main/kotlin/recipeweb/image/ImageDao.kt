package recipeweb.image

import com.google.inject.Inject
import ratpack.exec.Blocking
import ratpack.exec.Operation
import java.io.ByteArrayInputStream
import java.sql.Connection

class ImageDao @Inject constructor(
        private val connection: Connection
) {

    // https://www.postgresql.org/docs/7.4/jdbc-binary-data.html
    fun saveImage(createImageRequest: CreateImageRequest): Operation {
        val insertImage = "INSERT INTO image " +
                "(name, image) " +
                "VALUES (?,?)"
        val bStream = ByteArrayInputStream(createImageRequest.image)
        return Blocking.op {
            val pStatement = connection.prepareStatement(insertImage)
            pStatement.setString(1, createImageRequest.name)
            pStatement.setBinaryStream(2, bStream, createImageRequest.image.size)
            try {
                pStatement.executeUpdate()
            } catch (exception: Exception) {
                println("Failed to save image. ${exception.message}")
                throw exception
            }
        }
    }

}