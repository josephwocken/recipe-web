package recipeweb.image

import com.google.inject.Inject
import ratpack.exec.Operation
import ratpack.form.UploadedFile
import recipeweb.recipe.RecipeDao

class ImageService @Inject constructor(
        private val imageDao: ImageDao
) {

    //TODO: implement
    fun saveImage(uploadedFile: UploadedFile): Operation {
        val imageBytes: ByteArray = uploadedFile.bytes
        val createImageRequest = CreateImageRequest(
                "", //TODO: pass image name from front-end?
                imageBytes
        )
        return imageDao.saveImage(createImageRequest)
    }
}