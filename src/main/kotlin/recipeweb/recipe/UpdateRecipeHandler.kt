package recipeweb.recipe

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.TypedData
import ratpack.jackson.Jackson
import recipeweb.auth.UnAuthorizedException

class UpdateRecipeHandler @Inject constructor(
        private val recipeService: RecipeService,
        private val objectMapper: ObjectMapper
): Handler {
    override fun handle(ctx: Context) {
        ctx.request.body
                .map { typedData: TypedData? ->
                    val recipe: UpdateRecipeRequest = objectMapper.readValue(
                            typedData!!.text,
                            UpdateRecipeRequest::class.java
                    )
                    println("recipe: ${objectMapper.writeValueAsString(recipe)}")
                    recipe
                }
                .flatMap { recipe: UpdateRecipeRequest ->
                    recipeService.updateRecipe(recipe)
                }
                .mapError(
                        UnAuthorizedException::class.java,
                        { unAuthorizedException: UnAuthorizedException ->
                            println("Failed to update recipe because of authorization failure. ${unAuthorizedException.message}")
                            throw unAuthorizedException
                        }
                )
                .next { recipe: Recipe? ->
                    if (null === recipe) {
                        throw RecipeUpdateException()
                    }
                }
                .then { recipe: Recipe? ->
                    ctx.render(Jackson.json(recipe))
                }
    }
}