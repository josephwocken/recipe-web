package recipeweb.image

import com.google.inject.Inject
import ratpack.exec.Blocking
import ratpack.exec.Operation
import ratpack.exec.Promise
import java.io.ByteArrayInputStream
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class ImageDao @Inject constructor(
        private val connection: Connection
) {

    // https://www.postgresql.org/docs/7.4/jdbc-binary-data.html
    fun saveImage(createImageRequest: CreateImageRequest): Operation {
        val insertImage = "INSERT INTO image " +
                "(name, recipe_id, image) " +
                "VALUES (?,?,?)"
        val bStream = ByteArrayInputStream(createImageRequest.image)
        return Blocking.op {
            val pStatement = connection.prepareStatement(insertImage)
            pStatement.setString(1, createImageRequest.name)
            pStatement.setInt(2, createImageRequest.recipeId)
            pStatement.setBinaryStream(3, bStream, createImageRequest.image.size)
            try {
                pStatement.executeUpdate()
            } catch (exception: Exception) {
                println("Failed to save image. ${exception.message}")
                throw exception
            }
        }
    }

    fun readImagesByRecipeId(recipeId: Int): Promise<ByteArray?> {
        val selectImagesByRecipeId = "SELECT image FROM image " +
                "WHERE recipe_id = $recipeId"
        var image: ByteArray? = null
        return Blocking.get {
            connection.createStatement().use { statement: Statement ->
                try {
                    val rs: ResultSet = statement.executeQuery(selectImagesByRecipeId)
                    while(rs.next()) {
                        image = rs.getBytes("image")
                        return@use
                    }
                } catch (exception: Exception) {
                    println("Failed to read images for recipe-id=$recipeId. $exception.message")
                    throw exception
                }
            }
            image
        }
    }

}