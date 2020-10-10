package recipeweb

import com.fasterxml.jackson.annotation.JsonProperty

data class RecipeResponse(
        @JsonProperty("recipes") val recipes: List<Recipe>
)

data class Recipe(val name: String, val contents: String, val link: String)
