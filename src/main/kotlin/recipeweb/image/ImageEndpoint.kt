package recipeweb.image

import com.google.inject.Inject
import ratpack.func.Action
import ratpack.handling.ByMethodSpec
import ratpack.handling.Chain
import ratpack.handling.Context

class ImageEndpoint @Inject constructor(
        private val createImageHandler: CreateImageHandler,
        private val getImagesByRecipeIdHandler: GetImagesByRecipeIdHandler
): Action<Chain> {

    override fun execute(chain: Chain) {
        chain.path(":recipeId") { context: Context ->
            context.byMethod { byMethodSpec: ByMethodSpec ->
                byMethodSpec.post(createImageHandler)
                byMethodSpec.get(getImagesByRecipeIdHandler)
            }
        }
    }
}