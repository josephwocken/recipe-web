package recipeweb.image

import com.google.inject.Inject
import ratpack.exec.Operation
import ratpack.exec.Promise
import ratpack.form.UploadedFile
import recipeweb.recipe.RecipeDao

class ImageService @Inject constructor(
        private val imageDao: ImageDao
) {

    fun saveImage(recipeId: Int, uploadedFile: UploadedFile): Operation {
        val imageBytes: ByteArray = uploadedFile.bytes
        val createImageRequest = CreateImageRequest(
                name = "", //TODO: pass image name from front-end?,
                recipeId = recipeId,
                image = imageBytes
        )
        return imageDao.saveImage(createImageRequest)
    }

    fun getImagesByRecipeId(recipeId: Int): Promise<ByteArray?> {
        return imageDao.readImagesByRecipeId(recipeId)
    }
}