package recipeweb.recipe

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.TypedData
import recipeweb.auth.UnAuthorizedException
import recipeweb.user.UserService

class CreateRecipeHandler @Inject constructor(
        private val recipeService: RecipeService,
        private val objectMapper: ObjectMapper
): Handler {

    override fun handle(ctx: Context) {
        ctx.request.body
                .map { typedData: TypedData? ->
                    val recipe: CreateRecipeRequest = objectMapper.readValue(
                            typedData!!.text,
                            CreateRecipeRequest::class.java
                    )
                    println("recipe: ${objectMapper.writeValueAsString(recipe)}")
                    recipe
                }
                .nextOp { recipe: CreateRecipeRequest ->
                    recipeService.createRecipe(recipe)
                }
                .mapError(
                        UnAuthorizedException::class.java,
                        { unAuthorizedException: UnAuthorizedException ->
                            println("Failed to create recipe because of authorization failure. ${unAuthorizedException.message}")
                            throw unAuthorizedException
                        }
                )
                .then {
                    ctx.response
                            .status(201)
                            .send()
                }
    }
}