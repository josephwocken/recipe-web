package recipeweb.user

import com.google.inject.Inject
import ratpack.exec.Blocking
import ratpack.exec.Promise
import java.sql.Connection
import java.sql.Statement

class UserDao @Inject constructor(
        private val connection: Connection
) {

    fun selectMasterPassword(): Promise<String> {
        val selectPwd = "SELECT password FROM public.user " +
                "WHERE id = 1"
        return Blocking.get {
            var password: String? = null
            connection.createStatement().use { statement: Statement ->
                val rs = statement.executeQuery(selectPwd)
                while (rs.next()) {
                    password = rs.getString("password")
                    return@use
                }
            }
            password!!
        }
    }

}