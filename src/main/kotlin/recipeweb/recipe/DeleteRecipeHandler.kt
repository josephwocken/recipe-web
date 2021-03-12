package recipeweb.recipe

import com.google.inject.Inject
import ratpack.handling.Context
import ratpack.handling.Handler

class DeleteRecipeHandler @Inject constructor(
        private val recipeService: RecipeService
): Handler {

    override fun handle(ctx: Context) {
        val recipeId: String? = ctx.pathTokens["id"]
        val password: String? = ctx.request.queryParams["pwd"]
        if (null === recipeId || null === password) {
            ctx.response.status(422).send()
            return
        }
        return recipeService.deleteRecipe(recipeId, password)
                .then {
                    ctx.response
                            .status(200)
                            .send()
                }
    }

}