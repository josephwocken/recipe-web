package kotlinsandbox.handler

import com.google.inject.Inject
import ratpack.handling.ByMethodSpec
import ratpack.handling.Context
import ratpack.handling.Handler

class RecipeHandler @Inject constructor(
        private val getAllRecipesHandler: GetAllRecipesHandler,
        private val createRecipeHandler: CreateRecipeHandler
): Handler {
    override fun handle(ctx: Context) {
        ctx.byMethod { byMethodSpec: ByMethodSpec ->
            byMethodSpec.get(getAllRecipesHandler)
            byMethodSpec.post(createRecipeHandler)
        }
//        ctx.next() // this results in an error where the request body gets read twice
    }
}