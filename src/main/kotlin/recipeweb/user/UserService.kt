package recipeweb.user

import com.google.inject.Inject
import ratpack.exec.Operation
import recipeweb.auth.UnAuthorizedException

class UserService @Inject constructor(
        private val userDao: UserDao
) {

    fun validatePassword(providedPwd: String): Operation {
        return userDao.selectMasterPassword()
                .next { dbPassword: String ->
                    if (dbPassword != providedPwd) {
                        throw UnAuthorizedException()
                    }
                }
                .operation()
    }

}