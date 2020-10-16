package recipeweb.recipe

import com.google.inject.Inject
import ratpack.handling.Context
import ratpack.handling.Handler

class DeleteRecipeHandler @Inject constructor(
        private val recipeService: RecipeService
): Handler {

    override fun handle(ctx: Context) {
        val recipeId: String? = ctx.pathTokens["id"]
        if (null === recipeId) {
            ctx.response.status(422).send()
            return
        }
        return recipeService.deleteRecipe(recipeId)
                .then {
                    ctx.response
                            .status(200)
                            .send()
                }
    }

}