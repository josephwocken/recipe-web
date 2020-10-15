package recipeweb.recipe

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
                .map { recipes: List<Recipe> ->
                    val summaries: MutableList<RecipeSummary> = mutableListOf()
                    recipes.forEach { recipe: Recipe ->
                        val summary = RecipeSummary(
                                recipeId = recipe.recipeId,
                                content = recipe.content,
                                name = recipe.name
                        )
                        summaries.add(summary)
                    }
                    summaries
                }
                .then { recipes: List<RecipeSummary> ->
                    ctx.render(json(RecipeResponse(recipes)))
                }
    }
}