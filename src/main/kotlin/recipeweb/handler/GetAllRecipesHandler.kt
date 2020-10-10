package recipeweb.handler

import com.google.inject.Inject
import recipeweb.Recipe
import recipeweb.RecipeService
import ratpack.handling.ByMethodSpec
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson.json
import recipeweb.RecipeResponse

class GetAllRecipesHandler @Inject constructor(private val recipeService: RecipeService): Handler {
    override fun handle(ctx: Context) {
        return recipeService.getAllRecipes()
                .then { recipes: List<Recipe> ->
                    ctx.render(json(RecipeResponse(recipes)))
                }
    }
}