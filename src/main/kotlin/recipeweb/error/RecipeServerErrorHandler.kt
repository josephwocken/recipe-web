package recipeweb.error

import ratpack.error.ServerErrorHandler
import ratpack.handling.Context
import recipeweb.auth.UnAuthorizedException
import recipeweb.recipe.RecipeCreateException

class RecipeServerErrorHandler: ServerErrorHandler {
    override fun error(context: Context, throwable: Throwable) {
        if (throwable is UnAuthorizedException) {
            context.response
                    .status(401)
                    .send("Unauthorized")
        } else if (throwable is RecipeCreateException) {
            context.response
                    .status(400)
                    .send("Failed to create recipe")
        } else {
            context.response
                    .status(500)
                    .send()
        }
    }
}