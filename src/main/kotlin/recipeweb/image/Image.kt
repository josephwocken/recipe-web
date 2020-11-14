package recipeweb.image

data class Image(
        val imageId: Int,
        val name: String,
        val image: ByteArray,
        val recipeId: Int
)

data class CreateImageRequest(
        val name: String,
        val recipeId: Int,
        val image: ByteArray
)