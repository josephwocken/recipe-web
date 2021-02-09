package recipeweb.recipe

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.TypedData
import ratpack.jackson.Jackson.json
import recipeweb.auth.UnAuthorizedException
import recipeweb.image.CreateImageHandler
import recipeweb.user.UserService

class CreateRecipeHandler @Inject constructor(
        private val recipeService: RecipeService,
        private val objectMapper: ObjectMapper
): Handler {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CreateRecipeHandler::class.java)
    }

    override fun handle(ctx: Context) {
        ctx.request.body
                .map { typedData: TypedData? ->
                    try {
                        val recipe: CreateRecipeRequest = objectMapper.readValue(
                                typedData!!.text,
                                CreateRecipeRequest::class.java
                        )
                        log.info("recipe: ${objectMapper.writeValueAsString(recipe)}")
                        recipe
                    } catch (exception: Exception) {
                        log.error("Failed to deserialize create recipe request", exception)
                        throw exception
                    }
                }
                .flatMap { recipe: CreateRecipeRequest ->
                    recipeService.createRecipe(recipe)
                }
                .mapError(
                        UnAuthorizedException::class.java,
                        { unAuthorizedException: UnAuthorizedException ->
                            log.error("Failed to create recipe because of authorization failure", unAuthorizedException)
                            throw unAuthorizedException
                        }
                )
                .next { recipe: Recipe? ->
                    if (null === recipe) {
                        throw RecipeCreateException()
                    }
                }
                .then { recipe: Recipe? ->
                    ctx.render(json(recipe))
                }
    }
}