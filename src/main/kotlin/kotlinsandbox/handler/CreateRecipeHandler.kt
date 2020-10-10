package kotlinsandbox.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import kotlinsandbox.Recipe
import kotlinsandbox.RecipeService
import ratpack.exec.Promise
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.TypedData
import ratpack.jackson.Jackson.json

class CreateRecipeHandler @Inject constructor(
        private val recipeService: RecipeService,
        private val objectMapper: ObjectMapper
): Handler {

    override fun handle(ctx: Context) {
        ctx.request.body
                .map { typedData: TypedData? ->
                    val recipe: Recipe = objectMapper.readValue(
                            typedData!!.text,
                            Recipe::class.java
                    )
                    println("recipe: ${objectMapper.writeValueAsString(recipe)}")
                    recipe
                }
                .nextOp { recipe: Recipe ->
                    recipeService.createRecipe(recipe)
                }
                .then { recipe: Recipe ->
                    ctx.response
                            .headers
                            .add("Content-Type", "application/json")
                    ctx.response
                            .status(201)
                            .send(
                                    objectMapper.writeValueAsString(recipe)
                            )
                }
    }
}