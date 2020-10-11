package recipeweb.handler

import com.google.inject.Inject
import recipeweb.recipe.Recipe
import recipeweb.recipe.RecipeService
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson.json
import recipeweb.recipe.RecipeResponse

class GetAllRecipesHandler @Inject constructor(private val recipeService: RecipeService): Handler {
    override fun handle(ctx: Context) {
        return recipeService.getAllRecipes()
                .then { recipes: List<Recipe> ->
                    ctx.render(json(RecipeResponse(recipes)))
                }
    }
}