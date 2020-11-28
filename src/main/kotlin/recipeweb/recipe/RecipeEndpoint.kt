package recipeweb.recipe

import com.google.inject.Inject
import ratpack.func.Action
import ratpack.handling.ByMethodSpec
import ratpack.handling.Chain
import ratpack.handling.Context

class RecipeEndpoint @Inject constructor(
        private val createRecipeHandler: CreateRecipeHandler,
        private val getAllRecipesHandler: GetAllRecipesHandler,
        private val getRecipeByIdHandler: GetRecipeByIdHandler,
        private val deleteRecipeHandler: DeleteRecipeHandler,
        private val updateRecipeHandler: UpdateRecipeHandler
): Action<Chain> {
    override fun execute(chain: Chain) {
        chain.path("") { context: Context ->
                    context.byMethod { byMethodSpec: ByMethodSpec ->
                        byMethodSpec.get(getAllRecipesHandler)
                        byMethodSpec.post(createRecipeHandler)
                    }
                }
                .path(":id") { context: Context ->
                    context.byMethod { byMethodSpec: ByMethodSpec ->
                        byMethodSpec.get(getRecipeByIdHandler)
                        byMethodSpec.put(updateRecipeHandler)
                        byMethodSpec.delete(deleteRecipeHandler)
                    }
                }
    }
}