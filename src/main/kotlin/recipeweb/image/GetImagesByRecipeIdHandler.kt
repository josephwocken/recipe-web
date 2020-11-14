package recipeweb.image

import com.google.inject.Inject
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.Status

class GetImagesByRecipeIdHandler @Inject constructor(
        private val imageService: ImageService
): Handler {
    override fun handle(ctx: Context) {
        val recipeId = ctx.pathTokens["recipeId"]?.toInt()!!
        return imageService.getImagesByRecipeId(recipeId)
                .then { bytes: ByteArray? ->
                    if (null !== bytes) {
                        ctx.response.headers.set("Content-Type", "application/octet-stream")
                        ctx.response.status(Status.OK)
                                .send(bytes)
                    } else {
                        ctx.response.status(Status.NOT_FOUND)
                                .send()
                    }
                }
    }
}