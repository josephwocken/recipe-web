package kotlinsandbox.handler

import com.google.inject.Inject
import kotlinsandbox.Recipe
import kotlinsandbox.RecipeService
import ratpack.handling.ByMethodSpec
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson.json

class GetAllRecipesHandler @Inject constructor(private val recipeService: RecipeService): Handler {
    override fun handle(ctx: Context) {
        return recipeService.getAllRecipes()
                .then { recipes: List<Recipe> ->
                    ctx.render(json(recipes))
                }
    }
}