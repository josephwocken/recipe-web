package recipeweb.error

import ratpack.error.ServerErrorHandler
import ratpack.handling.Context
import recipeweb.auth.UnAuthorizedException

class RecipeServerErrorHandler: ServerErrorHandler {
    override fun error(context: Context, throwable: Throwable) {
        if (throwable is UnAuthorizedException) {
            context.response
                    .status(401)
                    .send()
        } else {
            context.response
                    .status(500)
                    .send()
        }
    }
}