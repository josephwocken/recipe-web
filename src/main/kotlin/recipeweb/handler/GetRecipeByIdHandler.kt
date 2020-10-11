package recipeweb.handler

import com.google.inject.Inject
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson.json
import recipeweb.recipe.Recipe
import recipeweb.recipe.RecipeService

class GetRecipeByIdHandler @Inject constructor(
        private val recipeService: RecipeService
): Handler {

    override fun handle(ctx: Context) {
        val recipeId: String? = ctx.pathTokens["id"]
        if (null === recipeId) {
            ctx.response.status(422).send()
            return
        }
        recipeService.getRecipe(recipeId)
                .then { recipe: Recipe? ->
                    if (null === recipe) {
                        ctx.response.status(404).send()
                    } else {
                        ctx.render(json(recipe))
                    }
                }
    }
}