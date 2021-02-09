package recipeweb.image

import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.exec.Operation
import ratpack.form.Form
import ratpack.form.UploadedFile
import ratpack.handling.Context
import ratpack.handling.Handler

class CreateImageHandler @Inject constructor(
        private val imageService: ImageService
): Handler {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(CreateImageHandler::class.java)
    }

    override fun handle(ctx: Context) {
        return ctx.parse(Form::class.java)
                .nextOp { form: Form ->
                    val recipeId = ctx.pathTokens["recipeId"]?.toInt()!!
                    //TODO: FIX, i'm not using the file name right
                    //TODO: may have to upload with a query parameter
                    //TODO: may have to change the front end code
                    val uploadedImageFile: UploadedFile? = form.file("image") // file extension?
                    if (uploadedImageFile === null) {
                        Operation.noop()
                    }
                    imageService.saveImage(recipeId, uploadedImageFile!!)
                }
                .mapError(
                        Exception::class.java,
                        { exception: Exception ->
                            log.error("Failed to read uploaded image file", exception)
                            throw exception
                        }
                )
                .then {
                    ctx.response
                            .status(201)
                            .send("Created")
                }
    }

}