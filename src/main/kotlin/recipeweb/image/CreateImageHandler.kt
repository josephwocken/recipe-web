package recipeweb.image

import com.google.inject.Inject
import ratpack.form.Form
import ratpack.form.UploadedFile
import ratpack.handling.Context
import ratpack.handling.Handler

class CreateImageHandler @Inject constructor(
        private val imageService: ImageService
): Handler {

    override fun handle(ctx: Context) {
        return ctx.parse(Form::class.java)
                .nextOp { form: Form ->
                    val recipeId = ctx.pathTokens["recipeId"]?.toInt()!!
                    //TODO: FIX, i'm not using the file name right
                    //TODO: may have to upload with a query parameter
                    //TODO: may have to change the front end code
                    val uploadedImageFile: UploadedFile = form.file("image") // file extension?
                    imageService.saveImage(recipeId, uploadedImageFile)
                }
                .mapError(
                        Exception::class.java,
                        {
                            val thrownException: Exception = it
                            println("Failed to read uploaded image file. ${thrownException.message}")
                            throw thrownException
                        }
                )
                .then {
                    ctx.response
                            .status(201)
                            .send("Created")
                }
    }

}