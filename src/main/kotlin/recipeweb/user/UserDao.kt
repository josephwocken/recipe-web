package recipeweb.user

import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.exec.Blocking
import ratpack.exec.Promise
import recipeweb.recipe.UpdateRecipeHandler
import java.sql.Connection
import java.sql.Statement

class UserDao @Inject constructor(
        private val connection: Connection
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserDao::class.java)
    }

    fun selectMasterPassword(): Promise<String?> {
        val selectPwd = "SELECT * FROM public.user WHERE id = 1"
        return Blocking.get {
            var password: String? = null
            connection.createStatement().use { statement: Statement ->
                try {
                    val rs = statement.executeQuery(selectPwd)
                    while (rs.next()) {
                        password = rs.getString("password")
                        return@use
                    }
                } catch (exception: Exception) {
                    log.error("Failed to select master password", exception)
                    throw exception
                }
            }
            password
        }
    }

}